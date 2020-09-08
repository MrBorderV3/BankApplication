package me.border.bankapplication.account;

import me.border.bankapplication.file.AccountFile;
import me.border.bankapplication.transaction.*;
import me.border.utilities.security.Encryptor;

import javax.crypto.SecretKey;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class AccountReader {
    private Encryptor decryptor;

    public AccountReader(SecretKey secretKey){
        // CREATE DECRYPTOR FROM AN EXISTING KEY SO IT CAN DECRYPT THE STRING
        this.decryptor = new Encryptor(secretKey);
    }

    public void readAccount(String id) {
        if (!AccountsManager.accountMap.containsKey(id)) {
            AccountFile accountFile = new AccountFile(id, new File("C:\\Users\\בית\\Desktop\\code\\Java\\BankApplication\\accounts"));
            accountFile.setup();

            String name = (String) accountFile.get("Name");
            String password = decryptor.decrypt((String) accountFile.get("Password"));
            double balance = (double) accountFile.get("Balance");
            Account account = new CustomerAccount(name, id, password, balance);
            AccountsManager.accountMap.put(id, account);

            for (int i = 1; true; i++) {
                String path = "Transactions." + i + ".";
                try {
                    if (accountFile.get(path + "id") == null)
                        break;

                    TransactionType transactionType = TransactionType.valueOf((String) accountFile.get(path + "type"));

                    Date date = DateFormat.getDateInstance().parse((String) accountFile.get(path + "date"));
                    double amount = (double) accountFile.get(path + "Balance");
                    double prior = (double) accountFile.get("prior");
                    double after = (double) accountFile.get("after");

                    if (transactionType == TransactionType.RECEIVE) {
                        String senderId = (String) accountFile.get("sender");
                        if (!AccountsManager.accountMap.containsKey(senderId)) {
                            readAccount(senderId);
                        }

                        Account senderAccount = AccountsManager.accountMap.get(id);
                        Transaction transaction = new ReceiveTransaction(account, senderAccount, amount, date, after, prior);
                        account.addTransaction(transaction);

                    } else if (transactionType == TransactionType.SEND) {
                        String receiverId = (String) accountFile.get("receiver");
                        if (!AccountsManager.accountMap.containsKey(receiverId)) {
                            readAccount(receiverId);
                        }

                        Account receiverAccount = AccountsManager.accountMap.get(id);
                        Transaction transaction = new SendTransaction(account, receiverAccount, amount, date, after, prior);
                        account.addTransaction(transaction);

                    } else if (transactionType == TransactionType.DEPOSIT) {
                        Transaction transaction = new DepositTransaction(account, amount, date, after, prior);
                        account.addTransaction(transaction);

                    } else {
                        Transaction transaction = new WithdrawTransaction(account, amount, date, after, prior);
                        account.addTransaction(transaction);
                    }

                    i++;
                } catch (NullPointerException e){
                    break;
                } catch (ParseException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
