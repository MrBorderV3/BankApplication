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

    protected CustomerAccount(String name, String id, String password){
        this.name = name;
        this.id = id;
        this.password = password;
    }

    protected CustomerAccount(String name, String id, String password, double startingBalance){
        this(name, id, password);
        this.balance = startingBalance;
    }

    protected CustomerAccount(String name, String id, String password, double startingBalance, List<Transaction> transactions){
        this(name, id, password, startingBalance);
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
    public boolean deposit(double amount) {
        if (amount > 10000) {
            return false;
        }
        addToBalance(amount);
        TransactionHandler.handleDeposit(this, amount);
        return true;
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
    public AccountComparator getComparator() {
        return new AccountComparator(this);
    }

}
