package com.example.Integradora_BibliotecaDigital.model;

import com.example.Integradora_BibliotecaDigital.ds.ArrayQueue;

public class Book {

    private int id;
    private String title;
    private String autor;
    private String category;

    private int totalCopies;
    private int availableCopies;
    private boolean active = true;

    private ArrayQueue<Integer> waitlist;


    public Book() {
    }

    public Book(int id, String title, String autor, int totalCopies) {
        this.id = id;
        this.title = title;
        this.autor = autor;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.active = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayQueue<Integer> getWaitlist() {
        if (waitlist == null) {
            waitlist = new ArrayQueue<>(4);
        }
        return waitlist;
    }

    public void setWaitlist(ArrayQueue<Integer> waitlist) {
        this.waitlist = waitlist;
    }
}

