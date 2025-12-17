package com.example.Integradora_BibliotecaDigital.repo;

import com.example.Integradora_BibliotecaDigital.ds.SinglyLinkedList;
import com.example.Integradora_BibliotecaDigital.ds.ArrayQueue;
import com.example.Integradora_BibliotecaDigital.ds.ArrayStack;
import com.example.Integradora_BibliotecaDigital.model.Book;
import com.example.Integradora_BibliotecaDigital.model.User;
import com.example.Integradora_BibliotecaDigital.model.Loan;
import com.example.Integradora_BibliotecaDigital.model.HistoryAction;

public class InMemoryDatabase {
    public final SinglyLinkedList<Book> books = new SinglyLinkedList<>();
    public final SinglyLinkedList<User> users = new SinglyLinkedList<>();
    public final SinglyLinkedList<Loan> loans = new SinglyLinkedList<>();

    private BookQueueNode queueHead = null;

    private static class BookQueueNode {
        long bookId;
        ArrayQueue<Long> queue;
        BookQueueNode next;

        BookQueueNode(long bookId) {
            this.bookId = bookId;
            this.queue = new ArrayQueue<>(4);
            this.next = null;
        }
    }

    public ArrayQueue<Long> getQueueForBook(long bookId) {
        BookQueueNode cur = queueHead;
        BookQueueNode prev = null;
        while (cur != null) {
            if (cur.bookId == bookId) {
                return cur.queue;
            }
            prev = cur;
            cur = cur.next;
        }
        BookQueueNode node = new BookQueueNode(bookId);
        if (prev == null) {
            queueHead = node;
        } else {
            prev.next = node;
        }
        return node.queue;
    }

    public final ArrayStack<HistoryAction> history = new ArrayStack<>(64);

    private int nextBookId = 1;
    private int nextUserId = 1;
    private long nextLoanId = 1;

    public synchronized int generateBookId() {
        return nextBookId++;
    }

    public synchronized int generateUserId() {
        return nextUserId++;
    }

    public synchronized long generateLoanId() {
        return nextLoanId++;
    }
}
