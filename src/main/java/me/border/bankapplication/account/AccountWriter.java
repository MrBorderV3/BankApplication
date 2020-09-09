package me.border.bankapplication.account;

import me.border.bankapplication.Main;
import me.border.bankapplication.file.AccountFile;
import me.border.bankapplication.transaction.ReceiveTransaction;
import me.border.bankapplication.transaction.SendTransaction;
import me.border.bankapplication.transaction.Transaction;
import me.border.bankapplication.transaction.TransactionType;
import me.border.utilities.security.Encryptor;

import java.io.File;

public class AccountWriter {

    private Encryptor encryptor;

    public AccountWriter(){
        this.encryptor = Main.getEncryptor();
    }

    public void writeAccount(Account account){
        AccountFile accountFile = new AccountFile(account.getID(), new File("C:\\Users\\בית\\Desktop\\code\\Java\\BankApplication\\accounts"));
        accountFile.setup();
        accountFile.set("Name", account.getName());
        accountFile.set("ID", account.getID());
        accountFile.set("Password", encryptor.encrypt(account.getPassword()));
        accountFile.set("Balance", account.getBalance());
        int i = 1;
        for (Transaction transaction : account.getTransactions()){
            if (transaction.getType() == TransactionType.RECEIVE){
                ReceiveTransaction receiveTransaction = (ReceiveTransaction) transaction;
                String path = "Transactions." + i + ".";
                accountFile.set(path + "type", receiveTransaction.getType().toString());
                accountFile.set(path + "date", receiveTransaction.getDate());
                accountFile.set(path + "amount", receiveTransaction.getTransactionAmount());
                accountFile.set(path = "sender", receiveTransaction.getSender().getID());
                accountFile.set(path + "prior", receiveTransaction.getPrior());
                accountFile.set(path + "after", receiveTransaction.getAfter());
            } else if (transaction.getType() == TransactionType.SEND){
                SendTransaction sendTransaction = (SendTransaction) transaction;
                String path = "Transactions." + i + ".";
                accountFile.set(path + "type", sendTransaction.getType().toString());
                accountFile.set(path + "date", sendTransaction.getDate());
                accountFile.set(path + "amount", sendTransaction.getTransactionAmount());
                accountFile.set(path = "receiver", sendTransaction.getReceiver().getID());
                accountFile.set(path + "prior", sendTransaction.getPrior());
                accountFile.set(path + "after", sendTransaction.getAfter());
            } else {
                String path = "Transactions." + i + ".";
                accountFile.set(path + "type", transaction.getType().toString());
                accountFile.set(path + "date", transaction.getDate());
                accountFile.set(path + "amount", transaction.getTransactionAmount());
                accountFile.set(path + "prior", transaction.getPrior());
                accountFile.set(path + "after", transaction.getAfter());
            }

            i++;
        }

        accountFile.save();
    }
}
