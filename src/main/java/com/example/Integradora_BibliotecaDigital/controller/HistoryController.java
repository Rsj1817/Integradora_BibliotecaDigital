package com.example.Integradora_BibliotecaDigital.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.Integradora_BibliotecaDigital.service.LibraryService;
import com.example.Integradora_BibliotecaDigital.model.HistoryAction;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    @Autowired
    private LibraryService service;

    @GetMapping
    public HistoryAction[] getHistory() {
        return service.getHistory();
    }

    @PostMapping("/undo")
    public ResponseEntity<String> undo() {
        String msg = service.undoLastAction();
        if (msg == null) {
            return ResponseEntity.badRequest().body("No hay acciones para deshacer o el tipo no es soportado.");
        }
        return ResponseEntity.ok(msg);
    }
}
