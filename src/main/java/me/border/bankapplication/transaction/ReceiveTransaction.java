package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public class ReceiveTransaction implements TWTransaction {

    private Account account;
    private Account sender;
    private double received;
    private double prior;
    private double after;
    private String date;

    protected ReceiveTransaction(Account account, Account sender, double received) {
        this.account = account;
        this.sender = sender;
        this.received = received;
        this.date = new Date().toString();
        this.after = account.getBalance();
        this.prior = after - received;
    }

    // FOR EXISTING TRANSACTIONS CONSTRUCTION
    public ReceiveTransaction(Account account, Account sender, double received, String date, double after, double prior){
        this.account = account;
        this.sender = sender;
        this.received = received;
        this.date = date;
        this.after = after;
        this.prior = prior;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.RECEIVE;
    }

    @Override
    public Account getAccount() {
        return account;
    }

    @Override
    public String getDate(){
        return date;
    }

    @Override
    public double getTransactionAmount(){
        return received;
    }

    @Override
    public double getPrior() {
        return prior;
    }

    @Override
    public double getAfter() {
        return after;
    }

    @Override
    public Account getParty(){
        return sender;
    }
}
