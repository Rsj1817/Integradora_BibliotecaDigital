package com.example.Integradora_BibliotecaDigital.ds;

public class SinglyLinkedList<T> {
    private static class Node<E> {
        E value;
        Node<E> next;

        Node(E v) {
            value = v;
            next = null;
        }
    }

    private Node<T> head;
    private int size = 0;

    public SinglyLinkedList() {
        head = null;
        size = 0;
    }

    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            Node<T> cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = node;
        }
        size++;
    }

    public T find(java.util.function.Predicate<T> predicate) {
        Node<T> cur = head;
        while (cur != null) {
            if (predicate.test(cur.value)) {
                return cur.value;
            }
            cur = cur.next;
        }
        return null;
    }

    public T[] toArray(java.util.function.IntFunction<T[]> ctor) {
        T[] arr = ctor.apply(size);
        Node<T> cur = head;
        int i = 0;
        while (cur != null) {
            arr[i++] = cur.value;
            cur = cur.next;
        }
        return arr;
    }

    public int size() {
        return size;
    }

    public boolean removeIf(java.util.function.Predicate<T> predicate) {
        Node<T> cur = head;
        Node<T> prev = null;
        while (cur != null) {
            if (predicate.test(cur.value)) {
                if (prev == null) {
                    head = cur.next;
                } else {
                    prev.next = cur.next;
                }
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }
}
