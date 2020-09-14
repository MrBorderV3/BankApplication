package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

/**
 * Interface for two-way transactions (Transactions with two accounts involved for example: {@link SendTransaction})
 * @see Transaction for more info on transactions
 */
public interface TWTransaction extends Transaction {

    /**
     * Get the other account in the two-way transaction
     *
     * @return The other account
     */
    Account getParty();
}
