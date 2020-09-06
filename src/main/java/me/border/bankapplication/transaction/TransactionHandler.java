package me.border.bankapplication.transaction;

import me.border.bankapplication.account.Account;
public class TransactionHandler {

    public static void handleSend(Account sender, Account receiver, double amountSent){
        handleReceive(receiver, sender, amountSent);
        sender.addTransaction(new SendTransaction(sender, receiver, amountSent));
    }

    public static void handleReceive(Account receiver, Account sender, double amountReceived){
        receiver.addTransaction(new ReceiveTransaction(receiver, sender, amountReceived));
    }

    public static void handleDeposit(Account account, double amountDeposited){
        account.addTransaction(new DepositTransaction(account, amountDeposited));
    }

    public static void handleWithdraw(Account account, double amountWithdrawn){
        account.addTransaction(new WithdrawTransaction(account, amountWithdrawn));
    }
}
