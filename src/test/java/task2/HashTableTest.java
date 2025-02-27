package task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование HashTable")
class HashTableTest {

    @Test
    @DisplayName("Вставка уникального ключа")
    void testInsertUnique() {
        HashTable table = new HashTable();
        table.insert(5);
        assertTrue(table.search(5), "Ключ 5 должен быть найден после вставки");
        assertEquals(1, table.size(), "Размер таблицы должен быть равен 1");
    }

    @Test
    @DisplayName("Вставка дубликата ключа не увеличивает размер")
    void testInsertDuplicate() {
        HashTable table = new HashTable();
        table.insert(5);
        table.insert(5); // повторная вставка
        assertTrue(table.search(5), "Ключ 5 должен быть найден");
        assertEquals(1, table.size(), "Размер таблицы не должен измениться при вставке дубликата");
    }

    @ParameterizedTest(name = "Проверка поиска для ключа {0}")
    @ValueSource(ints = {10, 20, 30})
    @DisplayName("Параметризованный тест поиска существующих ключей")
    void testSearchExisting(int key) {
        HashTable table = new HashTable();
        table.insert(key);
        assertTrue(table.search(key), "Ключ " + key + " должен быть найден");
    }

    @Test
    @DisplayName("Поиск несуществующего ключа")
    void testSearchNonExisting() {
        HashTable table = new HashTable();
        table.insert(10);
        assertFalse(table.search(20), "Ключ 20 не должен быть найден");
    }

    @Test
    @DisplayName("Удаление существующего ключа")
    void testRemoveExisting() {
        HashTable table = new HashTable();
        table.insert(5);
        assertTrue(table.search(5), "Ключ 5 должен быть найден до удаления");
        table.remove(5);
        assertFalse(table.search(5), "Ключ 5 не должен быть найден после удаления");
        assertEquals(0, table.size(), "Размер таблицы должен уменьшиться до 0");
    }

    @Test
    @DisplayName("Удаление несуществующего ключа не изменяет размер")
    void testRemoveNonExisting() {
        HashTable table = new HashTable();
        table.insert(5);
        table.remove(10); // ключ 10 отсутствует
        assertTrue(table.search(5), "Ключ 5 должен оставаться в таблице");
        assertEquals(1, table.size(), "Размер таблицы должен остаться неизменным");
    }
}
