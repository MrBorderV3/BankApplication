package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public class DepositTransaction implements Transaction {

    private Account account;
    private double deposit;
    private double prior;
    private double after;
    private String date;

    protected DepositTransaction(Account account, double deposit){
        this.account = account;
        this.deposit = deposit;
        this.date = new Date().toString();
        this.after = account.getBalance();
        this.prior = after - deposit;
    }

    // FOR EXISTING TRANSACTIONS CONSTRUCTION
    public DepositTransaction(Account account, double deposit, String date, double after, double prior){
        this.account = account;
        this.deposit = deposit;
        this.date = date;
        this.after = after;
        this.prior = prior;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.DEPOSIT;
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
        return deposit;
    }

    @Override
    public double getPrior() {
        return prior;
    }

    @Override
    public double getAfter() {
        return after;
    }
}
