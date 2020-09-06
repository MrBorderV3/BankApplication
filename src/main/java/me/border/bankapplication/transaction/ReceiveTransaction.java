package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public class ReceiveTransaction implements Transaction {

    private Account account;
    private Account sender;
    private double received;
    private double prior;
    private double after;
    private Date date;

    protected ReceiveTransaction(Account account, Account sender, double received) {
        this.account = account;
        this.sender = sender;
        this.received = received;
        this.date = new Date();
        this.after = account.getBalance();
        this.prior = after - received;
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
    public Date getDate(){
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

    public Account getSender(){
        return sender;
    }
}
