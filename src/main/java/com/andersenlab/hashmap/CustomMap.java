package com.andersenlab.hashmap;


public class CustomMap<K, V> {

    static class Entry<K, V> {
        final K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final String toString() {
            return key + "=" + value;
        }
    }

    private static final double LOAD_FACTOR = 0.7;
    private Entry<K, V>[] buckets;

    private int capacity = 16;
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int getIndex(K key) {
        int hashCod = key.hashCode();
        return hashCod % capacity;
    }

    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> head = buckets[index];

        while (head != null) {
            if (head.key.equals(key)) {
                return head.value;
            }
            head = head.next;
        }
        return null;
    }

    public V remove(K key) {
        int index = getIndex(key);
        Entry<K, V> head = buckets[index];

        if (head == null) {
            return null;
        }
        if (head.key.equals(key)) {
            V value = head.value;
            head = head.next;
            buckets[index] = head;
            size--;
            return value;
        } else {
            Entry<K, V> previous = null;
            while (head != null) {

                if (head.key.equals(key)) {
                    previous.next = head.next;
                    size--;
                    return head.value;
                }
                previous = head;
                head = head.next;
            }
            size--;
            return null;
        }
    }

    public void put(K key, V value) {
        if (buckets == null) {
            buckets = (Entry<K, V>[]) new Entry[capacity];
        }

        int index = getIndex(key);

        Entry<K, V> head = buckets[index];
        Entry<K, V> toAdd = new Entry<>(key, value, null);

        if (head == null) {
            buckets[index] = toAdd;
            size++;

        } else {
            while (head != null) {
                if (head.key.equals(key)) {
                    head.value = value;
                    size++;
                    break;
                }
                head = head.next;
            }
            if (head == null) {
                head = buckets[index];
                toAdd.next = head;
                buckets[index] = toAdd;
                size++;
            }
        }
        if ((1.0 * size) / capacity > LOAD_FACTOR) {
            resize();
        }
    }

    private void resize() {
        Entry<K, V>[] temporal = buckets;
        capacity = 2 * capacity;
        buckets = (Entry<K, V>[]) new Entry[capacity];

        for (Entry<K, V> headNode : temporal) {
            while (headNode != null) {
                put(headNode.key, headNode.value);
                headNode = headNode.next;
            }
        }
    }

    @Override
    public String toString() {
        String delimiter = " ";
        StringBuilder result = new StringBuilder();

        result
                .append("size:")
                .append(delimiter)
                .append(size)
                .append(delimiter);

        for (Entry<K, V> bucket : buckets) {
            while (bucket != null) {
                result.append(bucket);
                result.append(delimiter);
                bucket = bucket.next;
            }
        }
        return result.toString();
    }
}