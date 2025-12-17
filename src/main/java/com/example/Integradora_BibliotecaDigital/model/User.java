package com.example.Integradora_BibliotecaDigital.model;

public class User {

    private int id;
    private String name;
    private String email;


    public User() {
    }

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    //Getter y setter

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) { 
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

