package com.gp27.amis.datastructures;

import java.util.*;

public class Queue<T> implements java.util.Queue<T> {
    private final LinkedList<T> list;

    public Queue() {
        this.list = new LinkedList<>();
    }

    @Override
    public boolean add(T item) {
        list.addLast(item);
        return true;
    }

    public void enqueue(T item) {
        add(item);
    }

    @Override
    public boolean offer(T item) {
        return add(item);
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.removeFirst();
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return list.removeFirst();
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return list.getFirst();
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return list.getFirst();
    }

    public T dequeue() {
        return remove();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size()) {
            a = (T1[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size());
        }
        int i = 0;
        for (T item : this) {
            a[i++] = (T1) item;
        }
        return a;
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object o : c) {
            while (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        list.clear();
    }
}