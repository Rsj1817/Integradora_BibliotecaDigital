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
import com.example.Integradora_BibliotecaDigital.model.Loan;
import com.example.Integradora_BibliotecaDigital.model.Book;
import com.example.Integradora_BibliotecaDigital.model.User;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LibraryService service;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Loan req) {
        User user = service.getUser(req.getUserId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Book book = service.getBook(req.getBookId());
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        Loan l = service.createLoan(req.getBookId(), req.getUserId());
        if (l != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(l);
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(Map.of("message", "No hay copias disponibles. Has sido agregado a la lista de espera."));
        }
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<?> returnLoan(@PathVariable long loanId) {
        int result = service.returnLoan(loanId);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found");
        }
        if (result == 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Loan already returned");
        }
        if (result == 3) {
            return ResponseEntity.ok("Loan returned and reassigned to next user in waitlist");
        }
        return ResponseEntity.ok("Loan returned, no users in waitlist");
    }

    @GetMapping("/active")
    public Loan[] activeLoans() {
        return service.activeLoans();
    }

    @GetMapping("/user/{userId}")
    public Loan[] loansByUser(@PathVariable long userId) {
        return service.loansByUser(userId);
    }
}
