package me.border.bankapplication.account;

import me.border.bankapplication.transaction.Transaction;

import java.util.List;

public interface Account extends Cloneable {

    /**
     * Withdraw an amount from the account balance
     *
     * @param amount Amount to withdraw
     * @return Success of the action
     */
    boolean withdraw(double amount);

    /**
     * Deposit an amount to the account balance
     *
     * @param amount Amount to deposit
     * @return Success of the action
     */
    boolean deposit(double amount);

    /**
     * Send an amount to a different account
     *
     * @param account Account to send to
     * @param amount Amount to send
     * @return Success of the action
     */
    boolean send(Account account, double amount);

    /**
     * Get the balance of the account
     *
     * @return The balance
     */
    double getBalance();

    /**
     * Set the account balance
     *
     * @param balance The amount to set
     */
    void setBalance(double balance);

    /**
     * Add an amount to the current balance
     *
     * @param add The amount to add
     */
    void addToBalance(double add);

    /**
     * Remove an amount from the current balance
     *
     * @param remove The amount to remove
     */
    void removeFromBalance(double remove);

    /**
     * Get the name of the account owner
     *
     * @return The account owner name
     */
    String getName();

    /**
     * Get the ID of the account
     *
     * @return The account ID
     */
    String getID();

    /**
     * Get the password of the account
     *
     * @return The password
     */
    String getPassword();

    /**
     * Get the transaction history of the account
     *
     * @return The transaction history
     */
    List<Transaction> getTransactions();

    /**
     * Add a transaction to the transaction history
     *
     * @param transaction The transaction to add
     */
    void addTransaction(Transaction transaction);

    /**
     * Clone this account
     *
     * @return The cloned account
     */
    Account clone();
}
