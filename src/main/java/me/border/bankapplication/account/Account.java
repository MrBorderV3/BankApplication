package me.border.bankapplication.account;

import me.border.bankapplication.transaction.Transaction;

import java.util.List;

public interface Account {

    boolean withdraw(double amount);

    void deposit(double amount);

    boolean send(Account account, double amount);

    double getBalance();

    void setBalance(double balance);

    void addToBalance(double add);

    void removeFromBalance(double remove);

    String getName();

    String getID();

    String getPassword();

    List<Transaction> getTransactions();

    void addTransaction(Transaction transaction);
}
