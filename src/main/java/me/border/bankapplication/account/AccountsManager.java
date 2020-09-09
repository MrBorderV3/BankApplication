package me.border.bankapplication.account;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class AccountsManager {

    // String being the account ID and Account being the account
    protected static HashMap<String, Account> accountMap = new HashMap<>();
    public static List<String> nameList = new ArrayList<>();
    private static List<Account> clonedAccounts = new ArrayList<>();

    public static boolean login(String id, String password){
        if (accountMap.containsKey(id)){
            Account account = accountMap.get(id);
            String accountPassword = account.getPassword();
            boolean equals = password.equals(accountPassword);
            if (equals){
                if (!isTaskRunning){
                    initWriterTask();
                }
            }
            return equals;
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

    public static Account getAccount(String id){
        return accountMap.get(id);
    }

    private static boolean isTaskRunning = false;

    private static void initWriterTask(){
        isTaskRunning = true;
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                AccountWriter accountWriter = new AccountWriter();
                for (Account account : accountMap.values()) {
                    boolean matchingAccount = false;
                    ListIterator<Account> iterator = clonedAccounts.listIterator();
                    while (iterator.hasNext()){
                        Account clonedAcc = iterator.next();
                        if (account.getID().equals(clonedAcc.getID())) {
                            matchingAccount = true;
                            if (!(account.getTransactions().size() == clonedAcc.getTransactions().size())) {
                                accountWriter.writeAccount(account);
                                clonedAccounts.remove(clonedAcc);
                                clonedAccounts.add(account.clone());
                            }
                        }
                    }
                    if (!matchingAccount){
                        accountWriter.writeAccount(account);
                        clonedAccounts.add(account.clone());
                    }
                }
            }
        }, 5000, 5000);
    }
}
