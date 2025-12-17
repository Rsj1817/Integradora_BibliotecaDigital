package com.example.Integradora_BibliotecaDigital.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Integradora_BibliotecaDigital.service.LibraryService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    @Autowired
    private LibraryService service;

    @GetMapping("/book/{bookId}")
    public Long[] getReservationsForBook(@PathVariable long bookId) {
        return service.getReservationsForBook(bookId);
    }

    @DeleteMapping
    public String deleteReservation(@RequestParam long userId, @RequestParam long bookId) {
        boolean ok = service.cancelReservation(bookId, userId);
        return ok ? "Deleted" : "Not found";
    }
}

