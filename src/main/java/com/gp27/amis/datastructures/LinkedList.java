package com.gp27.amis.datastructures;

import java.util.*;

public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        Node(T data) {
            this.data = data;
        }
    }

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);
        if (tail == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        T removedData = head.data;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return removedData;
    }

    public T removeLast() {
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }
        T removedData = tail.data;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size--;
        return removedData;
    }

    @Override
    public boolean contains(Object o) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<T> current = head;
        while (current != null) {
            if (Objects.equals(current.data, o)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T e : c) {
            add(e);
        }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        for (T e : c) {
            add(index++, e);
        }
        return !c.isEmpty();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object e : c) {
            while (remove(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<T> current = head;
        while (current != null) {
            if (!c.contains(current.data)) {
                Node<T> next = current.next;
                remove(current.data);
                current = next;
                modified = true;
            } else {
                current = current.next;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T oldValue = current.data;
        current.data = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            Node<T> newNode = new Node<>(element);
            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.prev.next = current.next;
            current.next.prev = current.prev;
            size--;
            return current.data;
        }
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for (Node<T> current = head; current != null; current = current.next) {
            if (Objects.equals(o, current.data)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        for (Node<T> current = tail; current != null; current = current.prev) {
            if (Objects.equals(o, current.data)) {
                return index;
            }
            index--;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {
            private Node<T> lastReturned = null;
            private Node<T> next = head;
            private int nextIndex = 0;

            @Override
            public boolean hasNext() {
                return nextIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                lastReturned = next;
                next = next.next;
                nextIndex++;
                return lastReturned.data;
            }

            @Override
            public boolean hasPrevious() {
                return nextIndex > 0;
            }

            @Override
            public T previous() {
                if (!hasPrevious()) throw new NoSuchElementException();
                next = next == null ? tail : next.prev;
                lastReturned = next;
                nextIndex--;
                return lastReturned.data;
            }

            @Override
            public int nextIndex() {
                return nextIndex;
            }

            @Override
            public int previousIndex() {
                return nextIndex - 1;
            }

            @Override
            public void remove() {
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }
                Node<T> lastNext = lastReturned.next;
                if (lastReturned.prev == null) {
                    head = lastNext;
                } else {
                    lastReturned.prev.next = lastNext;
                }
                if (lastNext == null) {
                    tail = lastReturned.prev;
                } else {
                    lastNext.prev = lastReturned.prev;
                }
                if (next == lastReturned) {
                    next = lastNext;
                } else {
                    nextIndex--;
                }
                lastReturned = null;
                size--;
            }

            @Override
            public void set(T t) {
                if (lastReturned == null) throw new IllegalStateException();
                lastReturned.data = t;
            }

            @Override
            public void add(T t) {
                lastReturned = null;
                if (next == null) {
                    addLast(t);
                } else {
                    Node<T> newNode = new Node<>(t);
                    newNode.prev = next.prev;
                    newNode.next = next;
                    next.prev.next = newNode;
                    next.prev = newNode;
                    size++;
                }
                nextIndex++;
            }
        };
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<T> it = listIterator();
        for (int i = 0; i < index; i++) {
            it.next();
        }
        return it;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        LinkedList<T> subList = new LinkedList<>();
        Node<T> current = head;
        for (int i = 0; i < fromIndex; i++) {
            current = current.next;
        }
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(current.data);
            current = current.next;
        }
        return subList;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node<T> current = head; current != null; current = current.next) {
            array[i++] = current.data;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            a = (T1[]) java.lang.reflect.Array.newInstance(
                    a.getClass().getComponentType(), size);
        }
        int i = 0;
        Object[] result = a;
        for (Node<T> x = head; x != null; x = x.next) {
            result[i++] = x.data;
        }
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    public T getFirst() {
        if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
        return head.data;
    }

    public T getLast() {
        if (tail == null) {
            throw new NoSuchElementException("List is empty");
        }
        return tail.data;
    }

    public boolean offer(T t) {
        return add(t);
    }

    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return removeFirst();
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return removeFirst();
    }

    public T element() {
        return getFirst();
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return getFirst();
    }
}
