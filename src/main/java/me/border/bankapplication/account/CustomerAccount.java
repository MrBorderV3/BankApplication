package me.border.bankapplication.account;

import me.border.bankapplication.transaction.Transaction;
import me.border.bankapplication.transaction.TransactionHandler;

import java.util.ArrayList;
import java.util.List;

public class CustomerAccount implements Account {

    private String name;
    private String id;
    private String password;
    private double balance = 0;
    private List<Transaction> transactions = new ArrayList<>();

    public CustomerAccount(String name, String id, String password){
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public CustomerAccount(String name, String id, String password, double startingBalance){
        this(name, id, password);
        this.balance = startingBalance;
    }

    // TO DO - START UP ACCOUNTS (SERIALIZE THEM INTO YAMLS THEN TAKE INFO FROM YAMLS ON START UP AND BOOT UP THE ACCOUNTS USING THIS CONSTRUCTOR)
    private CustomerAccount(String name, String id, String password, double balance, List<Transaction> transactions){
        this(name, id, password, balance);
        this.transactions = transactions;
    }

    @Override
    public boolean withdraw(double amount){
        if (getBalance() < amount){
            return false;
        }
        removeFromBalance(amount);
        TransactionHandler.handleWithdraw(this, amount);
        return true;
    }

    @Override
    public void deposit(double amount) {
        addToBalance(amount);
        TransactionHandler.handleDeposit(this, amount);
    }

    @Override
    public boolean send(Account account, double amount) {
        if (getBalance() < amount){
            return false;
        }
        removeFromBalance(amount);
        account.addToBalance(amount);
        TransactionHandler.handleSend(this, account, amount);
        return true;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public void addToBalance(double add) {
        setBalance(getBalance() + add);
    }

    @Override
    public void removeFromBalance(double remove) {
        setBalance(getBalance() - remove);
    }

    @Override
    public String getName() {
        return name;
    }

    // WHEN SAVING THIS ENCRYPT IT
    @Override
    public String getID() {
        return id;
    }

    // WHEN SAVING THIS ENCRYPT IT
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public void addTransaction(Transaction transaction){
        this.transactions.add(transaction);
    }

    @Override
    public CustomerAccount clone() {
        try {
            return (CustomerAccount) super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
            return null;
        }
    }
}
