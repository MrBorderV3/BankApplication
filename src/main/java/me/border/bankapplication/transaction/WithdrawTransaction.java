package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public class WithdrawTransaction implements Transaction {

    private Account account;
    private double withdraw;
    private double prior;
    private double after;
    private String date;

    protected WithdrawTransaction(Account account, double withdraw){
        this.account = account;
        this.withdraw = withdraw;
        this.date = new Date().toString();
        this.after = account.getBalance();
        this.prior = after + withdraw;
    }

    // FOR EXISTING TRANSACTIONS CONSTRUCTION
    public WithdrawTransaction(Account account, double withdraw, String date, double after, double prior){
        this.account = account;
        this.withdraw = withdraw;
        this.date = date;
        this.after = after;
        this.prior = prior;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.WITHDRAW;
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
        return withdraw;
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
