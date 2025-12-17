package com.example.Integradora_BibliotecaDigital.ds;

@SuppressWarnings("unchecked")
public class ArrayStack<T> {
    private Object[] data;
    private int top = 0;

    public ArrayStack(int capacity) {
        data = new Object[Math.max(4, capacity)];
    }

    public void push(T item) {
        if (top == data.length) {
            Object[] nd = new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                nd[i] = data[i];
            }
            data = nd;
        }
        data[top++] = item;
    }

    public T pop() {
        if (top == 0) {
            return null;
        }
        T v = (T) data[--top];
        data[top] = null;
        return v;
    }

    public T peek() {
        if (top == 0) {
            return null;
        }
        return (T) data[top - 1];
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    public T[] toArray(java.util.function.IntFunction<T[]> ctor) {
        T[] arr = ctor.apply(top);
        for (int i = 0; i < top; i++) {
            arr[i] = (T) data[i];
        }
        return arr;
    }
}

