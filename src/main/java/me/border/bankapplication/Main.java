package me.border.bankapplication;

import me.border.bankapplication.account.AccountReader;
import me.border.bankapplication.file.SerializedKeyFile;
import me.border.bankapplication.ui.Interface;
import me.border.utilities.security.Encryptor;

import java.io.File;

public class Main {

    private static Encryptor encryptor = new Encryptor();
    private static Interface inf = new Interface();

    public static void main(String[] args) {
        startReader();
        inf.start(args);
    }

    private static void startReader(){
        SerializedKeyFile serializedKeyFile = new SerializedKeyFile("key", new File("C:\\Users\\בית\\Desktop\\code\\Java\\BankApplication\\security"), null);
        if (serializedKeyFile.getFile().exists()) {
            serializedKeyFile.setup();
            encryptor = new Encryptor(serializedKeyFile.getItem());
            AccountReader accountReader = new AccountReader(serializedKeyFile.getItem());
            File accountsFolder = new File("C:\\Users\\בית\\Desktop\\code\\Java\\BankApplication\\accounts");
            if (accountsFolder.exists() && accountsFolder.isDirectory()) {
                try {
                    File[] files = accountsFolder.listFiles();
                    if (files == null)
                        return;
                    for (File file : files) {
                        accountReader.readAccount(file.getName().replace(".yml", ""));
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        } else {
            serializedKeyFile.setItem(encryptor.getSecretKey());
            serializedKeyFile.setup();
        }

        serializedKeyFile.save();
    }

    public static Encryptor getEncryptor(){
        return encryptor;
    }
}
