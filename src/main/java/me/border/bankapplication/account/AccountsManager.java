package me.border.bankapplication.account;

import me.border.utilities.utils.ImmuteableResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

public class AccountsManager {

    // String being the account ID and Account being the account
    protected static HashMap<String, Account> accountMap = new HashMap<>();
    public static List<String> nameList = new ArrayList<>();
    private static List<AccountComparator> comparators = new ArrayList<>();

    public static ImmuteableResponse<Account> login(String id, String password){
        if (id.length() != 6 || password.length() <= 5 || password.contains("\\") || password.contains(" ") || password.contains("%") || password.contains("$")) {
            return new ImmuteableResponse<>(false);
        }

        if (accountMap.containsKey(id)){
            Account account = accountMap.get(id);
            String accountPassword = account.getPassword();
            boolean equals = password.equals(accountPassword);
            if (equals){
                if (!isTaskRunning){
                    initWriterTask();
                }
            }

            return new ImmuteableResponse<>(equals, account);
        } else {
            return new ImmuteableResponse<>(false);
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
        nameList.add(name);

        return account;
    }

    public static ImmuteableResponse<Account> getAccount(String id){
        if (!accountMap.containsKey(id)){
            return new ImmuteableResponse<>(false);
        }

        return new ImmuteableResponse<>(true, accountMap.get(id));
    }

    private static boolean isTaskRunning = false;

    private static void initWriterTask() {
        isTaskRunning = true;
        if (comparators.isEmpty()){
            for (Account account : accountMap.values()){
                comparators.add(account.getComparator());
            }
        }
        AccountWriter accountWriter = new AccountWriter();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Account account : accountMap.values()) {
                    boolean matchingAccount = false;
                    AccountComparator removalComp = null;
                    for (AccountComparator comparator : comparators) {
                        int comparison = comparator.compare(account);
                        if (comparison >= 1) {
                            matchingAccount = true;

                            if (comparison == 2) {
                                accountWriter.writeAccount(account);
                                removalComp = comparator;
                            }
                            break;
                        }
                    }

                    if (!matchingAccount) {
                        accountWriter.writeAccount(account);
                        comparators.add(account.getComparator());
                    } else if (removalComp != null){
                        comparators.remove(removalComp);
                        comparators.add(account.getComparator());
                    }


                }
            }
        }, 5000, 5000);
    }

    public static boolean credentialsCheck(String name, String password){
        if (password.length() <= 5 || password.contains("\\") || password.contains(" ") || password.contains("%") || password.contains("$")) {
            return false;
        }

        return !nameList.contains(name) &&name.length() > 3 && name.contains(" ") && !name.contains("\\");
    }
}
