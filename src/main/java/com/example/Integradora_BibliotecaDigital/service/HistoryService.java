package com.example.Integradora_BibliotecaDigital.service;

import com.example.Integradora_BibliotecaDigital.ds.ArrayStack;
import com.example.Integradora_BibliotecaDigital.model.HistoryAction;

public class HistoryService {

    private ArrayStack<HistoryAction> stack = new ArrayStack<>(100);

    public void push(HistoryAction action) {
        stack.push(action);
    }

    public HistoryAction pop() {
        return stack.pop();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}

