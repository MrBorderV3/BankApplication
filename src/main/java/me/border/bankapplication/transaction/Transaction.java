package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;

import java.util.Date;

public interface Transaction {

    TransactionType getType();

    Account getAccount();

    Date getDate();

    double getTransactionAmount();

    double getPrior();

    double getAfter();
}
