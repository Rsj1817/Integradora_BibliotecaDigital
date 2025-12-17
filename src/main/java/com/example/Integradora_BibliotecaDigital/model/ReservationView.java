package com.example.Integradora_BibliotecaDigital.model;

public class ReservationView {

    private int position;
    private int userId;
    private String userName;

    public ReservationView() {
    }

    public ReservationView(int position, int userId, String userName) {
        this.position = position;
        this.userId = userId;
        this.userName = userName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
