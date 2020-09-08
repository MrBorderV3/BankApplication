package me.border.bankapplication;

import me.border.bankapplication.account.Account;
import me.border.bankapplication.account.AccountReader;
import me.border.bankapplication.account.AccountsManager;
import me.border.bankapplication.file.SerializedKeyFile;
import me.border.utilities.security.Encryptor;

import java.io.File;
import java.util.Scanner;

public class Main {

    private static Encryptor encryptor = new Encryptor();

    public static void main(String[] args) {
        startReader();
        showStartingMenu();
    }

    private static void displayStartup(){
        println("-------------------------------------------");
        println("Welcome Customer");
        println("");
        println("A. Login");
        println("B. Create an account");
    }

    private static void showStartingMenu() {
        char option;
        Scanner optionScanner = new Scanner(System.in);
        displayStartup();

        do {
            String scan = optionScanner.next();
            if (scan.length() != 1) {
                println("Invalid option! Please enter a new option!");
            }
            option = scan.charAt(0);
            switch (option) {
                case 'a':
                case 'A':
                    println("Please enter your account ID");
                    Scanner idScanner = new Scanner(System.in);
                    String id = idScanner.next();
                    println("Please enter your account Password");
                    Scanner passwordScanner = new Scanner(System.in);
                    String password = passwordScanner.next();
                    if (id.length() != 6 || password.length() <= 5 || password.contains("\\") || password.contains(" ") || password.contains("%") || password.contains("$")) {
                        println("Incorrect account id or password");
                        displayStartup();
                    }
                    if (AccountsManager.login(id, password)) {
                        Account yourAccount = AccountsManager.getAccount(id);
                        println("Successfully logged in!");
                    } else {
                        println("Incorrect account id or password");
                        displayStartup();
                    }
                    break;
                case 'b':
                case 'B':
                    println("We're glad you wish to create an account!");
                    println("-------------------------------------------");
                    println("To start off please type in your full name.");
                    Scanner nameScanner = new Scanner(System.in);
                    String name = nameScanner.nextLine();
                    if (name.length() <= 5 || !name.contains(" ") || name.contains("\\")) {
                        println("Invalid name!");
                        displayStartup();
                        break;
                    }
                    println("-------------------------------------------");
                    println("Please enter a password of your choice");
                    Scanner passwordScanner1 = new Scanner(System.in);
                    String password1 = passwordScanner1.next();
                    if (password1.length() <= 5 || password1.contains("\\") || password1.contains(" ") || password1.contains("%") || password1.contains("$")) {
                        println("Invalid password! A password must be more then 6 letters and may not contain illegal characters!");
                        displayStartup();
                        break;
                    }
                    Account yourAccount = AccountsManager.createAccount(name, password1);
                    println("You have successfully created an account! Your id is `" + yourAccount.getID() + "` Make sure you keep that ID in a safe place!");
                    displayStartup();
                    break;
                default:
                    if (option != 'c')
                        println("Invalid option! Please enter a new option!");
            }
        } while (option != 'C' && option != 'c');
        println("Thank you for using our services");
        System.exit(0);
    }

    private static void println(String string){
        System.out.println(string);
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
