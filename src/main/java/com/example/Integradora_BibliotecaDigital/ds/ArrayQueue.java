package com.example.Integradora_BibliotecaDigital.ds;

@SuppressWarnings("unchecked")
public class ArrayQueue<T> {
    private Object[] data;
    private int head = 0;
    private int tail = 0;
    private int count = 0;

    public ArrayQueue(int capacity) {
        data = new Object[Math.max(4, capacity)];
    }

    private void ensureCapacity() {
        if (count < data.length) {
            return;
        }
        int newCap = data.length * 2;
        Object[] nd = new Object[newCap];
        for (int i = 0; i < count; i++) {
            nd[i] = data[(head + i) % data.length];
        }
        data = nd;
        head = 0;
        tail = count % data.length;
    }

    public void enqueue(T item) {
        ensureCapacity();
        data[tail] = item;
        tail = (tail + 1) % data.length;
        count++;
    }

    public T dequeue() {
        if (count == 0) {
            return null;
        }
        T v = (T) data[head];
        data[head] = null;
        head = (head + 1) % data.length;
        count--;
        return v;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public boolean removeIf(java.util.function.Predicate<T> predicate) {
        if (count == 0) {
            return false;
        }
        int n = 0;
        Object[] nd = new Object[data.length];
        boolean removed = false;
        for (int i = 0; i < count; i++) {
            T v = (T) data[(head + i) % data.length];
            if (!removed && predicate.test(v)) {
                removed = true;
                continue;
            }
            nd[n++] = v;
        }
        if (removed) {
            data = nd;
            head = 0;
            tail = n % data.length;
            count = n;
        }
        return removed;
    }

    public int indexOf(java.util.function.Predicate<T> predicate) {
        for (int i = 0; i < count; i++) {
            T v = (T) data[(head + i) % data.length];
            if (predicate.test(v)) {
                return i;
            }
        }
        return -1;
    }
}

