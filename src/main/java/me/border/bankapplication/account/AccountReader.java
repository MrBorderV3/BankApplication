package me.border.bankapplication.account;

import me.border.bankapplication.file.AccountFile;
import me.border.bankapplication.transaction.*;
import me.border.utilities.security.Encryptor;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class AccountReader {
    private Encryptor decryptor;

    public AccountReader(){
        // CREATE DECRYPTOR FROM AN EXISTING KEY SO IT CAN DECRYPT THE STRING
        this.decryptor = new Encryptor(/*KEY*/);
    }

    public void readAccount(String id) throws ParseException {
        AccountFile accountFile = new AccountFile(id, new File("/data"));
        accountFile.setup();
        String name = (String) accountFile.get("Name");
        String password = decryptor.decrypt((String) accountFile.get("Password"));
        double balance = (double) accountFile.get("Balance");
        for (int i = 1; true; i++){
            String path = "Transactions." + i + ".";
            TransactionType transactionType = TransactionType.valueOf((String) accountFile.get(path + "type"));
            Date date = DateFormat.getDateInstance().parse((String) accountFile.get(path + "date"));
            double amount = (double) accountFile.get(path + "Balance");
            double prior = (double) accountFile.get("prior");
            double after = (double) accountFile.get("after");
            if (transactionType == TransactionType.RECEIVE){
                String senderId = (String) accountFile.get("sender");
                // CREATE A EXISTING TRANSACTION CONSTRUCTOR FOR TRANSACTIONS AND INTITALIZE IT HERE
            } else if (transactionType == TransactionType.SEND){
                String receiverId = (String) accountFile.get("receiver");
                // CREATE A EXISTING TRANSACTION CONSTRUCTOR FOR TRANSACTIONS AND INTITALIZE IT HERE
            } else if (transactionType == TransactionType.DEPOSIT){

                // CREATE A EXISTING TRANSACTION CONSTRUCTOR FOR TRANSACTIONS AND INTITALIZE IT HERE
            } else {
                String receiverId = (String) accountFile.get("receiver");
                // CREATE A EXISTING TRANSACTION CONSTRUCTOR FOR TRANSACTIONS AND INTITALIZE IT HERE
            }
            accountFile.save();

            i++;
        }
    }
}
