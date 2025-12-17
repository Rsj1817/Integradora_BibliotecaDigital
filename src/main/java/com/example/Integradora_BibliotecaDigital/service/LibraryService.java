package com.example.Integradora_BibliotecaDigital.service;

import org.springframework.stereotype.Service;
import com.example.Integradora_BibliotecaDigital.repo.InMemoryDatabase;
import com.example.Integradora_BibliotecaDigital.model.Book;
import com.example.Integradora_BibliotecaDigital.model.User;
import com.example.Integradora_BibliotecaDigital.model.Loan;
import com.example.Integradora_BibliotecaDigital.model.HistoryAction;
import com.example.Integradora_BibliotecaDigital.ds.ArrayQueue;

@Service
public class LibraryService {
    public final InMemoryDatabase db = new InMemoryDatabase();

    public Book addBook(Book book) {
        book.setId(db.generateBookId());
        db.books.add(book);
        return book;
    }

    public Book[] listBooks() {
        return db.books.toArray(Book[]::new);
    }

    public Book getBook(long id) {
        return db.books.find(b -> b.getId() == id && b.isActive());
    }

    public Book updateBook(long id, Book updated) {
        Book b = db.books.find(x -> x.getId() == id);
        if (b == null) {
            return null;
        }
        b.setTitle(updated.getTitle() != null ? updated.getTitle() : b.getTitle());
        b.setAutor(updated.getAutor() != null ? updated.getAutor() : b.getAutor());
        if (updated.getTotalCopies() > 0) {
            int diff = updated.getTotalCopies() - b.getTotalCopies();
            b.setTotalCopies(updated.getTotalCopies());
            b.setAvailableCopies(b.getAvailableCopies() + diff);
        }
        return b;
    }

    public Book patchStatus(long id, boolean active) {
        Book b = db.books.find(x -> x.getId() == id);
        if (b == null) {
            return null;
        }
        b.setActive(active);
        return b;
    }

    public Book[] searchByTitle(String title) {
        Book[] all = db.books.toArray(Book[]::new);
        int cnt = 0;
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i].getTitle() != null && all[i].getTitle().toLowerCase().contains(title.toLowerCase())) {
                cnt++;
            }
        }
        Book[] res = new Book[cnt];
        int j = 0;
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null && all[i].getTitle() != null && all[i].getTitle().toLowerCase().contains(title.toLowerCase())) {
                res[j++] = all[i];
            }
        }
        return res;
    }

    public User addUser(User u) {
        u.setId(db.generateUserId());
        db.users.add(u);
        return u;
    }

    public User[] listUsers() {
        return db.users.toArray(User[]::new);
    }

    public User getUser(long id) {
        return db.users.find(x -> x.getId() == id);
    }

    public Loan createLoan(long bookId, long userId) {
        Book b = db.books.find(x -> x.getId() == bookId);
        if (b == null || !b.isActive()) {
            return null;
        }
        int prev = b.getAvailableCopies();
        if (b.getAvailableCopies() > 0) {
            long loanId = db.generateLoanId();
            Loan loan = new Loan(loanId, bookId, userId);
            db.loans.add(loan);
            b.setAvailableCopies(prev - 1);
            HistoryAction h = new HistoryAction();
            h.setActionType("CREATE_LOAN");
            h.setUserId(userId);
            h.setBookId(bookId);
            h.setLoanId(loanId);
            h.setPreviousAvailableCopies(prev);
            db.history.push(h);
            return loan;
        } else {
            ArrayQueue<Integer> q = b.getWaitlist();
            q.enqueue((int) userId);
            HistoryAction h = new HistoryAction();
            h.setActionType("ADD_TO_WAITLIST");
            h.setUserId(userId);
            h.setBookId(bookId);
            h.setLoanId(-1);
            h.setPreviousAvailableCopies(prev);
            db.history.push(h);
            return null;
        }
    }

    public Loan[] activeLoans() {
        Loan[] all = db.loans.toArray(Loan[]::new);
        int cnt = 0;
        for (int i = 0; i < all.length; i++) {
            Loan loan = all[i];
            if (loan != null && !loan.isReturned()) {
                cnt++;
            }
        }
        Loan[] res = new Loan[cnt];
        int k = 0;
        for (int i = 0; i < all.length; i++) {
            Loan loan = all[i];
            if (loan != null && !loan.isReturned()) {
                res[k++] = loan;
            }
        }
        return res;
    }

    public Loan[] loansByUser(long userId) {
        Loan[] all = db.loans.toArray(Loan[]::new);
        int cnt = 0;
        for (int i = 0; i < all.length; i++) {
            Loan loan = all[i];
            if (loan != null && !loan.isReturned() && loan.getUserId() == userId) {
                cnt++;
            }
        }
        Loan[] res = new Loan[cnt];
        int k = 0;
        for (int i = 0; i < all.length; i++) {
            Loan loan = all[i];
            if (loan != null && !loan.isReturned() && loan.getUserId() == userId) {
                res[k++] = loan;
            }
        }
        return res;
    }

    public int returnLoan(long loanId) {
        Loan loan = db.loans.find(l -> ((Loan) l).getId() == loanId);
        if (loan == null) {
            return 1;
        }
        if ("RETURNED".equals(loan.getStatus()) || loan.isReturned()) {
            return 2;
        }
        loan.setStatus("RETURNED");
        loan.setReturned(true);
        Book b = db.books.find(x -> x.getId() == loan.getBookId());
        if (b == null) {
            return 0;
        }
        ArrayQueue<Integer> q = b.getWaitlist();
        boolean reassigned = false;
        if (!q.isEmpty()) {
            int nextUserId = q.dequeue();
            long newLoanId = db.generateLoanId();
            Loan newLoan = new Loan(newLoanId, loan.getBookId(), nextUserId);
            db.loans.add(newLoan);
            reassigned = true;
        } else {
            b.setAvailableCopies(b.getAvailableCopies() + 1);
        }
        return reassigned ? 3 : 0;
    }

    public int getReservationPosition(long bookId, long userId) {
        Book b = db.books.find(x -> x.getId() == bookId);
        if (b == null) {
            return -1;
        }
        ArrayQueue<Integer> q = b.getWaitlist();
        return q.indexOf(u -> u == (int) userId);
    }

    public boolean cancelReservation(long bookId, long userId) {
        Book b = db.books.find(x -> x.getId() == bookId);
        if (b == null) {
            return false;
        }
        ArrayQueue<Integer> q = b.getWaitlist();
        return q.removeIf(u -> u == (int) userId);
    }

    public Long[] getReservationsForBook(long bookId) {
        Book b = db.books.find(x -> x.getId() == bookId);
        if (b == null) {
            return new Long[0];
        }
        ArrayQueue<Integer> q = b.getWaitlist();
        int n = q.size();
        Long[] tmp = new Long[n];
        for (int i = 0; i < n; i++) {
            Integer v = q.dequeue();
            tmp[i] = v == null ? null : v.longValue();
            q.enqueue(v);
        }
        return tmp;
    }

    public HistoryAction[] getHistory() {
        if (db.history.isEmpty()) {
            return new HistoryAction[0];
        }
        return db.history.toArray(HistoryAction[]::new);
    }

    public String undoLastAction() {
        if (db.history.isEmpty()) {
            return null;
        }
        HistoryAction h = db.history.pop();
        if (h == null) {
            return null;
        }
        String type = h.getActionType();
        if ("CREATE_LOAN".equals(type)) {
            boolean removed = db.loans.removeIf(l -> ((Loan) l).getId() == h.getLoanId());
            Book b = db.books.find(x -> x.getId() == h.getBookId());
            if (b != null) {
                b.setAvailableCopies(h.getPreviousAvailableCopies());
            }
            if (!removed && b == null) {
                return null;
            }
            return "Se deshizo la creación del préstamo. La copia volvió a estar disponible.";
        } else if ("ADD_TO_WAITLIST".equals(type)) {
            Book b = db.books.find(x -> x.getId() == h.getBookId());
            if (b == null) {
                return null;
            }
            ArrayQueue<Integer> q = b.getWaitlist();
            ArrayQueue<Integer> temp = new ArrayQueue<>(q.size());
            while (!q.isEmpty()) {
                Integer current = q.dequeue();
                if (current != null && current != (int) h.getUserId()) {
                    temp.enqueue(current);
                }
            }
            b.setWaitlist(temp);
            return "Se deshizo la reserva del usuario en la lista de espera.";
        }
        return null;
    }
}
