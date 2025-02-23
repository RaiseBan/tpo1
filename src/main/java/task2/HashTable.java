package task2;

import java.util.LinkedList;

public class HashTable {
    private static final int DEFAULT_CAPACITY = 16;

    private LinkedList<Integer>[] table;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.size = 0;
        this.table = new LinkedList[this.capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("initialCapacity must be > 0");
        }
        this.capacity = initialCapacity;
        this.size = 0;
        this.table = new LinkedList[this.capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public void insert(int key) {
        int index = hash(key);
        LinkedList<Integer> bucket = table[index];
        // Если ключа нет в текущем бакете — добавляем
        if (!bucket.contains(key)) {
            bucket.add(key);
            size++;
        }
    }

    public boolean search(int key) {
        int index = hash(key);
        LinkedList<Integer> bucket = table[index];
        return bucket.contains(key);
    }

    public void remove(int key) {
        int index = hash(key);
        LinkedList<Integer> bucket = table[index];
        if (bucket.remove((Integer) key)) {
            size--;
        }
    }

    public int size() {
        return size;
    }

    private int hash(int key) {
        return Math.abs(key) % capacity;
    }
}
