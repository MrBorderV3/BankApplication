package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public interface Transaction {

    /**
     * Get the transaction type
     *
     * @return The transaction type
     */
    TransactionType getType();

    /**
     * Get the account the transaction belongs to
     *
     * @return The account
     */
    Account getAccount();

    /**
     * Get the date and time of the transaction
     *
     * @return The date and time of the transaction
     */
    Date getDate();

    /**
     * Get the amount transacted in the transaction
     *
     * @return The amount transacted
     */
    double getTransactionAmount();

    /**
     * Get the account's balance prior to the transaction
     *
     * @return The balance prior to the transaction
     */
    double getPrior();

    /**
     * Get the account's balance after the transaction
     *
     * @return The balance after the transaction
     */
    double getAfter();
}
