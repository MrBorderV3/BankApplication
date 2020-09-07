package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public class SendTransaction implements Transaction {

    private Account account;
    private Account receiver;
    private double sent;
    private double prior;
    private double after;
    private Date date;

    protected SendTransaction(Account account, Account receiver, double sent){
        this.account = account;
        this.receiver = receiver;
        this.sent = sent;
        this.date = new Date();
        this.after = account.getBalance();
        this.prior = after + sent;
    }

    // FOR EXISTING TRANSACTIONS CONSTRUCTION
    public SendTransaction(Account account, Account receiver, double sent, Date date, double after, double prior){
        this.account = account;
        this.receiver = receiver;
        this.sent = sent;
        this.date = date;
        this.after = after;
        this.prior = prior;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.SEND;
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
        return sent;
    }

    @Override
    public double getPrior() {
        return prior;
    }

    @Override
    public double getAfter() {
        return after;
    }

    public Account getReceiver(){
        return receiver;
    }
}
