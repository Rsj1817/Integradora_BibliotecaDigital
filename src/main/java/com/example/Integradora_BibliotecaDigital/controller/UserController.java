package com.example.Integradora_BibliotecaDigital.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.Integradora_BibliotecaDigital.service.LibraryService;
import com.example.Integradora_BibliotecaDigital.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private LibraryService service;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User u) {
        User x = service.addUser(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(x);
    }

    @GetMapping
    public User[] listUsers() {
        return service.listUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User u = service.getUser(id);
        if (u == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(u);
    }
}

