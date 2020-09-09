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
                    emptyLn();
                    printSep();
                    println("Please enter your account ID");
                    printSep();
                    Scanner idScanner = new Scanner(System.in);
                    String id = idScanner.next();
                    println("Please enter your account Password");
                    Scanner passwordScanner = new Scanner(System.in);
                    String password = passwordScanner.next();
                    if (id.length() != 6 || password.length() <= 5 || password.contains("\\") || password.contains(" ") || password.contains("%") || password.contains("$")) {
                        println("Incorrect account id or password");
                        displayStartup();
                        break;
                    }

                    if (AccountsManager.login(id, password)) {
                        Account yourAccount = AccountsManager.getAccount(id);
                        showLoginMenu(yourAccount);
                    } else {
                        println("Incorrect account id or password");
                        displayStartup();
                        break;
                    }

                case 'b':
                case 'B':
                    printSep();
                    println("We're glad you wish to create an account!");
                    emptyLn();
                    println("To start off please type in your full name.");
                    printSep();
                    Scanner nameScanner = new Scanner(System.in);
                    String name = nameScanner.nextLine();
                    nameScanner.close();
                    if (name.length() <= 5 || !name.contains(" ") || name.contains("\\")) {
                        println("Invalid name!");
                        displayStartup();
                        break;
                    }
                    printSep();
                    println("Please enter a password of your choice");
                    Scanner passwordScanner1 = new Scanner(System.in);
                    String password1 = passwordScanner1.nextLine();
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

    private void showLoginMenu(Account account) {
        char option;
        Scanner optionScanner = new Scanner(System.in);
        displayLogin(account);

        do {
            String scan = optionScanner.next();
            if (scan.length() != 1) {
                println("Invalid option! Please enter a new option!");
            }
            option = scan.charAt(0);
            switch (option) {
                case 'a':
                case 'A':
                    emptyLn();
                    printSep();
                    println("Your balance: $" + account.getBalance());
                    printSep();
                    emptyLn();
                    break;
                case 'b':
                case 'B':
                    emptyLn();
                    printSep();
                    println("Please select the amount you wish to deposit");
                    printSep();
                    Scanner depositScanner = new Scanner(System.in);
                    double amount;
                    try {
                        amount = depositScanner.nextDouble();
                    } catch (Exception e) {
                        println("Invalid amount!");
                        emptyLn();
                        break;
                    }
                    if (!account.deposit(amount)) {
                        println("A customer account may not deposit more then $10,000 in one deposit.");
                        emptyLn();
                        break;
                    } else {
                        emptyLn();
                        println("Successfully deposited $" + amount + " to your account balance!");
                    }

                    emptyLn();
                    break;
                case 'c':
                case 'C':
                    emptyLn();
                    printSep();
                    println("Please select the amount you wish to withdraw");
                    printSep();
                    Scanner withdrawScanner = new Scanner(System.in);
                    double wAmount;

                    try {
                        wAmount = withdrawScanner.nextDouble();
                    } catch (Exception e) {
                        println("Invalid amount!");
                        emptyLn();
                        break;
                    }
                    if (!account.withdraw(wAmount)) {
                        println("You do not have enough balance to withdraw that amount.");
                        emptyLn();
                        break;
                    } else {
                        emptyLn();
                        println("Successfully withdrawn $" + wAmount + " from your account balance!");
                    }

                    emptyLn();
                    break;
                default:
                    if (option != 'G' && option != 'g')
                        println("Invalid option! Please enter a new option!");
            }
        } while (option != 'G' && option != 'g');
        println("Thank you for using our services! You will now be logged out of your account");
        showMainMenu();
    }

    private void displayLogin(Account account){
        println("Welcome back " + account.getName() + "!");
        printSep();
        emptyLn();
        println("A. My Balance");
        println("B. Deposit");
        println("C. Withdraw");
        println("D. Send");
        println("E. Transaction History");
        println("F. Settings");
        println("G. Logout");
    }

    private void displayStartup(){
        printSep();
        println("Welcome Customer");
        printSep();
        emptyLn();
        println("A. Login");
        println("B. Create an account");
        println("C. Exit");
    }

    private void emptyLn(){
        println("");
    }

    private void printSep(){
        println("-------------------------------------------");
    }

    private void println(String string){
        System.out.println(string);
    }
}
