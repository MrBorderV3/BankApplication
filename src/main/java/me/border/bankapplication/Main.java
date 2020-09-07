package me.border.bankapplication;

import me.border.bankapplication.account.AccountReader;
import me.border.bankapplication.file.SerializedKeyFile;
import me.border.utilities.security.Encryptor;

import java.io.File;

public class Main {

    private static Encryptor encryptor = new Encryptor();

    public static void main(String[] args) {
        startReader();
    }

    private static void startReader(){
        SerializedKeyFile serializedKeyFile = new SerializedKeyFile("key", new File("/security"), encryptor.getSecretKey());
        serializedKeyFile.setup();
        if (serializedKeyFile.getFile().exists()) {
            AccountReader accountReader = new AccountReader(serializedKeyFile.getItem());
            File accountsFolder = new File("/accounts");
            if (accountsFolder.exists() && accountsFolder.isDirectory()) {
                try {
                    File[] files = accountsFolder.listFiles();
                    if (files == null)
                        return;
                    for (File file : files) {
                        accountReader.readAccount(file.getName());
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }

        serializedKeyFile.save();
    }

    public static Encryptor getEncryptor(){
        return encryptor;
    }
}
