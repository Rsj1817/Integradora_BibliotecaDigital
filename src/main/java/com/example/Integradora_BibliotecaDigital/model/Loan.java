package com.example.Integradora_BibliotecaDigital.model;

public class Loan {

    private int id;
    private int userId;
    private int bookId;
    private boolean returned = false;
    private String status;

    public Loan() {
    }

    public Loan(long id, long bookId, long userId) {
        this.id = (int) id;
        this.bookId = (int) bookId;
        this.userId = (int) userId;
        this.status = "ACTIVE";
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public boolean isReturned() {
        return returned;
    }

    public String getStatus() {
        return status;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
