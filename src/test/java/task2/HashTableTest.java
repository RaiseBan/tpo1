package task2;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {


    static class CollisionKey {
        private final String data;

        CollisionKey(String data) {
            this.data = data;
        }

        @Override
        public int hashCode() {
            return 42;
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
    public void testCollisionHandling() {
        HashTable<CollisionKey, String> table = new HashTable<>();
        CollisionKey key1 = new CollisionKey("First");
        CollisionKey key2 = new CollisionKey("Second");

        table.put(key1, "value1");

        assertNull(table.get(key2),
                "При правильной обработке коллизий key2 не должен находить value1");

        table.put(key2, "value2");

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
        assertNull(table.get(key2));

        Method getBucketIndexMethod = HashTable.class.getDeclaredMethod("getBucketIndex", Object.class);
        getBucketIndexMethod.setAccessible(true);
        int indexForKey2 = (int) getBucketIndexMethod.invoke(table, key2);

        Field bucketsField = HashTable.class.getDeclaredField("buckets");
        bucketsField.setAccessible(true);
        LinkedList<?>[] buckets = (LinkedList<?>[]) bucketsField.get(table);

        LinkedList<?> bucket = buckets[indexForKey2];
        assertEquals(1, bucket.size());

        Object entryObject = bucket.get(0);
        Field keyField = entryObject.getClass().getDeclaredField("key");
        keyField.setAccessible(true);
        CollisionKey storedKey = (CollisionKey) keyField.get(entryObject);

        assertSame(key1, storedKey);
    }


    @Test
    public void testGetOperationIsO1() {
        HashTable<Integer, String> table = new HashTable<>();
        int numberOfElements = 10_000;

        for (int i = 0; i < numberOfElements; i++) {
            table.put(i, "value" + i);
        }

        Random random = new Random();
        int[] randomKeys = new int[1000];
        for (int i = 0; i < randomKeys.length; i++) {
            randomKeys[i] = random.nextInt(numberOfElements);
        }

        long totalTime = 0;
        int iterations = 1000;

        for (int i = 0; i < iterations; i++) {
            int key = randomKeys[i % randomKeys.length]; // Циклически используем ключи
            long startTime = System.nanoTime();
            table.get(key);
            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        double averageTime = (double) totalTime / iterations;

        assertTrue(averageTime < 1_000,
                "Среднее время выполнения операции get должно быть меньше 1 микросекунды. Фактическое время: " + averageTime + " наносекунд");

        System.out.println("Среднее время выполнения операции get: " + averageTime + " наносекунд");
    }


}
