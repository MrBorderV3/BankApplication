package me.border.bankapplication;

import me.border.bankapplication.account.Account;
import me.border.bankapplication.account.AccountsManager;

import java.util.Scanner;

public class Interface {

    public Interface(){
    }

    public void start(){
        showMainMenu();
    }

    private void showMainMenu() {
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
                    idScanner.close();
                    println("Please enter your account Password");
                    Scanner passwordScanner = new Scanner(System.in);
                    String password = passwordScanner.next();
                    passwordScanner.close();
                    if (id.length() != 6 || password.length() <= 5 || password.contains("\\") || password.contains(" ") || password.contains("%") || password.contains("$")) {
                        println("Incorrect account id or password");
                        displayStartup();
                    }
                    if (AccountsManager.login(id, password)) {
                        Account yourAccount = AccountsManager.getAccount(id);
                        println("Successfully logged in!");
                        optionScanner.close();
                        showLoginMenu();
                        break;
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
                    nameScanner.close();
                    if (name.length() <= 5 || !name.contains(" ") || name.contains("\\")) {
                        println("Invalid name!");
                        displayStartup();
                        break;
                    }
                    println("-------------------------------------------");
                    println("Please enter a password of your choice");
                    Scanner passwordScanner1 = new Scanner(System.in);
                    String password1 = passwordScanner1.next();
                    passwordScanner1.close();
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

    private void showLoginMenu(){

    }

    private void displayStartup(){
        println("-------------------------------------------");
        println("Welcome Customer");
        println("");
        println("A. Login");
        println("B. Create an account");
    }

    private void println(String string){
        System.out.println(string);
    }
}
