package com.example.Integradora_BibliotecaDigital.model;

public class HistoryAction {


    public enum ActionType {
        CREATE_LOAN,
        RETURN_LOAN,
        ADD_TO_WAITLIST
    }

    public ActionType type;
    public long bookId;
    public long userId;
    public long loanId;
    public int prevAvailableCopies;

    public HistoryAction() {
    }

    public HistoryAction(ActionType type, long bookId, long userId, long loanId, int prevAvailableCopies) {
        this.type = type;
        this.bookId = bookId;
        this.userId = userId;
        this.loanId = loanId;
        this.prevAvailableCopies = prevAvailableCopies;
    }

    public void setActionType(String actionType) {
        if (actionType == null) {
            this.type = null;
        } else {
            this.type = ActionType.valueOf(actionType);
        }
    }

    public String getActionType() {
        return type == null ? null : type.name();
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setPreviousAvailableCopies(int previousAvailableCopies) {
        this.prevAvailableCopies = previousAvailableCopies;
    }

    public int getPreviousAvailableCopies() {
        return prevAvailableCopies;
    }
}
