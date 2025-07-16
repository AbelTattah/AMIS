package com.gp27.amis.datastructures;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Simplified implementation of HashMap with basic Map interface support
 * @param <K> Key type
 * @param <V> Value type
 */
public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    
    private static class Node<K, V> implements Map.Entry<K, V> {
        final K key;
        V value;
        Node<K, V> next;
        final int hash;
        
        Node(K key, V value, Node<K, V> next, int hash) {
            this.key = key;
            this.value = value;
            this.next = next;
            this.hash = hash;
        }
        
        @Override
        public K getKey() {
            return key;
        }
        
        @Override
        public V getValue() {
            return value;
        }
        
        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        
        @Override
        public final boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                return Objects.equals(key, e.getKey()) &&
                       Objects.equals(value, e.getValue());
            }
            return false;
        }
        
        @Override
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
        
        @Override
        public final String toString() {
            return key + "=" + value;
        }
    }
    
    private Node<K,V>[] table;
    private int size;
    private int threshold;
    
    @SuppressWarnings({"unchecked"})
    public HashMap() {
        this.table = (Node<K,V>[])new Node[DEFAULT_CAPACITY];
        this.threshold = (int)(DEFAULT_CAPACITY * LOAD_FACTOR);
    }
    
    private int hash(Object key) {
        if (key == null) return 0;
        int h = key.hashCode();
        return h ^ (h >>> 16); // Spread bits to reduce collisions
    }
    
    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        
        // Check if key already exists
        Node<K,V> node = table[index];
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }
        
        // Add new node at beginning of chain
        addNode(key, value, hash, index);
        return null;
    }
    
    private void addNode(K key, V value, int hash, int index) {
        Node<K,V> first = table[index];
        table[index] = new Node<>(key, value, first, hash);
        size++;
        
        if (size > threshold) {
            resize();
        }
    }
    
    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K,V>[] oldTable = table;
        int oldCap = (oldTable == null) ? 0 : oldTable.length;
        int newCap = (oldCap == 0) ? DEFAULT_CAPACITY : oldCap << 1;
        
        threshold = (int)(newCap * LOAD_FACTOR);
        Node<K,V>[] newTable = (Node<K,V>[])new Node[newCap];
        table = newTable;
        
        if (oldTable != null) {
            for (int i = 0; i < oldCap; i++) {
                Node<K,V> node = oldTable[i];
                while (node != null) {
                    Node<K,V> next = node.next;
                    int index = (newCap - 1) & node.hash;
                    node.next = newTable[index];
                    newTable[index] = node;
                    node = next;
                }
            }
        }
    }
    
    @Override
    public V get(Object key) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        
        Node<K,V> node = table[index];
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }
    
    @Override
    public V remove(Object key) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        
        Node<K,V> node = table[index];
        Node<K,V> prev = null;
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node.value;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<Entry<K,V>> entrySet() {
        Set<Entry<K,V>> entries = new HashSet<>();
        for (Node<K,V> node : table) {
            while (node != null) {
                entries.add(node);
                node = node.next;
            }
        }
        return entries;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Node<K,V> node : table) {
            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }
        return values;
    }
    
    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }
    
    @Override
    public boolean containsValue(Object value) {
        for (Node<K,V> node : table) {
            while (node != null) {
                if (Objects.equals(value, node.value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }
    
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Node<K,V> node : table) {
            while (node != null) {
                keys.add(node.key);
                node = node.next;
            }
        }
        return keys;
    }
    
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (Node<K,V> node : table) {
            while (node != null) {
                action.accept(node.key, node.value);
                node = node.next;
            }
        }
    }
    
    // ===== Unimplemented Map methods =====
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public V putIfAbsent(K key, V value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public V replace(K key, V value) {
        throw new UnsupportedOperationException();
    }
}