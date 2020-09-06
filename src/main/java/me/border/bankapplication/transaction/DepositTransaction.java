package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public class DepositTransaction implements Transaction {

    private Account account;
    private double deposit;
    private double prior;
    private double after;
    private Date date;

    protected DepositTransaction(Account account, double deposit){
        this.account = account;
        this.deposit = deposit;
        this.date = new Date();
        this.after = account.getBalance();
        this.prior = after - deposit;
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
    public Date getDate(){
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
