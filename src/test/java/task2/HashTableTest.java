package task2;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {

    // Класс для проверки коллизий:
    // У каждого объекта — один и тот же hashCode, но разные поля data => разные equals().
    static class CollisionKey {
        private final String data;

        CollisionKey(String data) {
            this.data = data;
        }

        @Override
        public int hashCode() {
            return 42; // Один хэш на всех
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            CollisionKey other = (CollisionKey) obj;
            return data.equals(other.data);
        }
    }

    @Test
    public void testPut() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        assertEquals(1, table.get("one"));
        table.put("one", 10);
        assertEquals(10, table.get("one"));
    }

    @Test
    public void testGet() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("two", 2);
        table.put("three", 3);

        assertEquals(2, table.get("two"));
        assertEquals(3, table.get("three"));
        assertNull(table.get("four"));
    }

    @Test
    public void testRemove() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("A", 100);
        table.put("B", 200);

        assertEquals(100, table.remove("A"));
        assertNull(table.get("A"));
        assertEquals(1, table.size());
    }

    @Test
    public void testSize() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("X", 1);
        table.put("Y", 2);
        table.put("Z", 3);

        assertEquals(3, table.size());
        table.remove("Y");
        assertEquals(2, table.size());
    }

    /**
     * Проверка коллизий, подтверждающая работу именно как хэш-таблицы:
     * - Два объекта с одинаковым hashCode, но разным equals.
     * - Сначала кладём (key1 -> value1), убеждаемся, что по key2 вернётся null.
     * - После добавляем (key2 -> value2), проверяем, что всё лежит корректно.
     */
    @Test
    public void testCollisionHandling() {
        HashTable<CollisionKey, String> table = new HashTable<>();
        CollisionKey key1 = new CollisionKey("First");
        CollisionKey key2 = new CollisionKey("Second");

        // Кладём (key1 -> "value1")
        table.put(key1, "value1");

        // Должно вернуться null по key2, т. к. equals() !=
        assertNull(table.get(key2),
                "При правильной обработке коллизий key2 не должен находить value1");

        // Теперь кладём (key2 -> "value2")
        table.put(key2, "value2");

        // Убеждаемся, что обе записи доступны
        assertEquals("value1", table.get(key1));
        assertEquals("value2", table.get(key2));
    }



    @Test
    @SuppressWarnings("unchecked")
    public void testSameHashButDifferentObjectsInsideOneBucket() throws Exception {
        HashTable<CollisionKey, String> table = new HashTable<>();
        CollisionKey key1 = new CollisionKey("AAA");
        CollisionKey key2 = new CollisionKey("BBB");

        table.put(key1, "ValueForAAA");
        assertNull(table.get(key2));  // equals у key1 и key2 -> false, поэтому null

        Method getBucketIndexMethod = HashTable.class.getDeclaredMethod("getBucketIndex", Object.class);
        getBucketIndexMethod.setAccessible(true);
        int indexForKey2 = (int) getBucketIndexMethod.invoke(table, key2);

        Field bucketsField = HashTable.class.getDeclaredField("buckets");
        bucketsField.setAccessible(true);
        LinkedList<?>[] buckets = (LinkedList<?>[]) bucketsField.get(table);

        LinkedList<?> bucket = buckets[indexForKey2];
        assertEquals(1, bucket.size());  // key1 уже лежит в этом бакете

        Object entryObject = bucket.get(0);
        Field keyField = entryObject.getClass().getDeclaredField("key");
        keyField.setAccessible(true);
        CollisionKey storedKey = (CollisionKey) keyField.get(entryObject);

        assertSame(key1, storedKey);  // внутри лежит key1, а не key2, хотя хэш у них один
    }

}
