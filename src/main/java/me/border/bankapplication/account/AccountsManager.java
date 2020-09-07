package me.border.bankapplication.account;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AccountsManager {

    // String being the account ID and Account being the account
    public static HashMap<String, Account> accountMap = new HashMap<>();

    public static boolean login(String id, String password){
        if (!isTaskRunning){
            initWriterTask();
        }
        if (accountMap.containsKey(id)){
            Account account = accountMap.get(id);
            String accountPassword = account.getPassword();
            return password.equals(accountPassword);
        } else {
            return false;
        }
    }

    public static Account createAccount(String name, String password) {
        if (!isTaskRunning){
            initWriterTask();
        }
        String id = RandomStringUtils.random(6, true, true).toUpperCase();
        // Make sure that account ID doesn't exist already
        while (accountMap.containsKey(id)){
            id = RandomStringUtils.random(6, true, true).toUpperCase();
        }
        Account account = new CustomerAccount(name, id, password);
        accountMap.put(id, account);

        return account;
    }

    private static boolean isTaskRunning = false;

    private static void initWriterTask(){
        isTaskRunning = true;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AccountWriter accountWriter = new AccountWriter();
                for (Account account : accountMap.values()){
                    accountWriter.writeAccount(account);
                }
            }
        }, 30000, 10000);
    }
}
