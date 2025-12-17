package com.example.Integradora_BibliotecaDigital.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Integradora_BibliotecaDigital.model.Book;
import com.example.Integradora_BibliotecaDigital.service.LibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private LibraryService service;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book b = service.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(b);
    }

    @GetMapping
    public Book[] listBooks() {
        return service.listBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        Book b = service.getBook(id);
        if (b == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(b);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Book> patchStatus(@PathVariable long id, @RequestParam boolean active) {
        Book b = service.patchStatus(id, active);
        if (b == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(b);
    }
}
