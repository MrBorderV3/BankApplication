package me.border.bankapplication.account;

import me.border.utilities.security.Encryptor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;

public class AccountsManager {

    // String being the account ID and Account being the account
    private static HashMap<String, Account> accountMap = new HashMap<>();

    // THIS WILL BE USED TO ENCRYPT ACCOUNT DETAILS WHEN WRITTEN TO FILE.
    private static Encryptor accountEncryptor = new Encryptor();

    public static boolean login(String id, String password){
        if (accountMap.containsKey(id)){
            Account account = accountMap.get(id);
            String accountPassword = account.getPassword();
            return password.equals(accountPassword);
        } else {
            return false;
        }
    }

    public static Account createAccount(String name, String password) {
        String id = RandomStringUtils.random(6, true, true).toUpperCase();
        // Make sure that account ID doesn't exist already
        while (accountMap.containsKey(id)){
            id = RandomStringUtils.random(6, true, true).toUpperCase();
        }
        Account account = new CustomerAccount(name, id, password);
        accountMap.put(id, account);

        return account;
    }
}
