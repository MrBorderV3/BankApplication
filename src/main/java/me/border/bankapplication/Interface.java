package me.border.bankapplication;

import me.border.bankapplication.account.Account;
import me.border.bankapplication.account.AccountsManager;
import me.border.bankapplication.transaction.*;

import java.util.Date;
import java.util.List;
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
                handleError("option! Please enter a new option!");
            }
            option = scan.charAt(0);
            switch (option) {
                case 'a':
                case 'A':
                    request("enter your account ID");
                    Scanner idScanner = new Scanner(System.in);
                    String id = idScanner.next();
                    request("enter your account Password");
                    Scanner passwordScanner = new Scanner(System.in);
                    String password = passwordScanner.next();
                    if (id.length() != 6 || password.length() <= 5 || password.contains("\\") || password.contains(" ") || password.contains("%") || password.contains("$")) {
                        handleError("ID or password!");
                        displayStartup();
                        break;
                    }

                    if (AccountsManager.login(id, password)) {
                        Account yourAccount = AccountsManager.getAccount(id);
                        showLoginMenu(yourAccount);
                    } else {
                        handleError("ID or password!");
                        displayStartup();
                        break;
                    }

                case 'b':
                case 'B':
                    printSep();
                    println("We're glad you wish to create an account!");
                    request("type in your full name.");
                    Scanner nameScanner = new Scanner(System.in);
                    String name = nameScanner.nextLine();
                    if (name.length() <= 5 || !name.contains(" ") || name.contains("\\")) {
                        handleError("name!");
                        displayStartup();
                        break;
                    }
                    if (AccountsManager.nameList.contains(name)){
                        handleError("name! This name is already registered in our database.");
                        displayStartup();
                        break;
                    }
                    request("enter a password of your choice");
                    Scanner passwordScanner1 = new Scanner(System.in);
                    String password1 = passwordScanner1.nextLine();
                    passwordScanner1.close();
                    if (password1.length() <= 5 || password1.contains("\\") || password1.contains(" ") || password1.contains("%") || password1.contains("$")) {
                        handleError("password! A password must be more then 6 letters and may not contain illegal characters!");
                        displayStartup();
                        break;
                    }
                    Account yourAccount = AccountsManager.createAccount(name, password1);
                    println("You have successfully created an account! Your id is `" + yourAccount.getID() + "` Make sure you keep that ID in a safe place!");
                    displayStartup();
                    break;
                default:
                    if (option != 'C' && option != 'c')
                        handleError("option! Please enter a new option!");
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
                handleError("option! Please enter a new option.");
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
                    request("select the amount you wish to deposit");
                    Scanner depositScanner = new Scanner(System.in);
                    double amount;
                    try {
                        amount = depositScanner.nextDouble();
                    } catch (Exception e) {
                        handleError("amount!");
                        break;
                    }
                    if (!account.deposit(amount)) {
                        handleError("amount! A customer account may not deposit more then $10,000 in one deposit.");
                        break;
                    } else {
                        emptyLn();
                        println("Successfully deposited $" + amount + " to your account balance!");
                    }

                    emptyLn();
                    break;
                case 'c':
                case 'C':
                    request("select the amount you wish to withdraw");
                    Scanner withdrawScanner = new Scanner(System.in);
                    double wAmount;

                    try {
                        wAmount = withdrawScanner.nextDouble();
                    } catch (Exception e) {
                        handleError("amount!");
                        break;
                    }
                    if (!account.withdraw(wAmount)) {
                        handleError("amount! You do not have enough balance to withdraw that amount.");
                        break;
                    } else {
                        emptyLn();
                        println("Successfully withdrawn $" + wAmount + " from your account balance!");
                    }

                    emptyLn();
                    break;
                case 'd':
                case 'D':
                    request("enter the ID of the account you wish to send balance to.");
                    Scanner receiverScanner = new Scanner(System.in);
                    String receiverId = receiverScanner.next();
                    if (receiverId.equals(account.getID())) {
                        handleError("ID! You may not send money to yourself!");
                        break;
                    }
                    Account receiverAccount = AccountsManager.getAccount(receiverId);
                    if (receiverAccount != null){
                        Scanner sendAmountScanner = new Scanner(System.in);
                        double sAmount;
                        try {
                            sAmount = sendAmountScanner.nextDouble();
                        } catch (Exception e){
                            handleError("amount");
                            break;
                        }

                        if (account.send(receiverAccount, sAmount)){
                            emptyLn();
                            println("Successfully sent " + receiverId + " $" + sAmount + "!");
                        } else {
                            handleError("amount! You do not have enough money in your balance to perform this transaction.");
                            break;
                        }
                    } else {
                        handleError("ID! Please enter an ID of an existing account.");
                        break;
                    }
                case 'e':
                case 'E':
                    if (account.getTransactions().isEmpty()){
                        emptyLn();
                        println("You do not have any transactions yet!");
                        break;
                    }
                    showTransactionsMenu(account);
                    break;
                case 'f':
                case 'F':
                    // DISPLAY THE ACCOUNT DETAILS = A
                    // CHANGE PASSWORD = B
                default:
                    if (option != 'G' && option != 'g')
                        handleError("option! Please enter a different option.");
            }
        } while (option != 'G' && option != 'g');
        println("Thank you for using our services! You will now be logged out of your account");
        showMainMenu();
    }

    private void showTransactionsMenu(Account account) {
        char tOption;
        emptyLn();
        printSep();
        println("A. Full Transaction History");
        println("B. Previous transaction");
        println("C. Choose A Transaction");
        println("D. Back");

        Scanner transactionOptionScanner = new Scanner(System.in);
        do {
            String transactionScan = transactionOptionScanner.next();
            if (transactionScan.length() != 1) {
                handleError("option! Please enter a new option.");
            }
            tOption = transactionScan.charAt(0);
            List<Transaction> transactionHistory = account.getTransactions();
            switch (tOption) {
                case 'a':
                case 'A':
                    int id = 1;
                    for (Transaction transaction : transactionHistory) {
                        printTransaction(transaction, id);
                        id++;
                    }
                    break;
                case 'b':
                case 'B':
                    Transaction latest = transactionHistory.get(transactionHistory.size() - 1);
                    printTransaction(latest, transactionHistory.size());
                    break;
                case 'c':
                case 'C':
                    request("enter the ID of the transaction you wish to see.");
                    Scanner tIdScanner = new Scanner(System.in);
                    int pickedTid;
                    try {
                        pickedTid = tIdScanner.nextInt();
                    } catch (Exception e) {
                        handleError("id!");
                        break;
                    }
                    if (transactionHistory.isEmpty()) {
                        emptyLn();
                        println("You do not have any transactions yet!");
                        displayLogin(account);
                        break;
                    }
                    int actualTId = pickedTid - 1;
                    Transaction picked = transactionHistory.get(actualTId);
                    if (picked == null) {
                        handleError("id!");
                        break;
                    }
                    printTransaction(picked, pickedTid);
                    break;
                default:
                    if (tOption != 'D' && tOption != 'd')
                        handleError("option! Please enter a different option.");
            }
        } while (tOption != 'D' && tOption != 'd');
        println("Heading back to the account menu!");
        emptyLn();
        displayLogin(account);
    }

    private void printTransaction(Transaction transaction, int id){
        TransactionType type = transaction.getType();
        String date = transaction.getDate();
        double prior = transaction.getPrior();
        double after = transaction.getAfter();
        double amountTransacted = transaction.getTransactionAmount();
        switch (type){
            case SEND:
                printSep();
                println("ID: " + id);
                println("Type: SEND");
                println("Date: " + date);
                println("Amount Sent: " + amountTransacted);
                println("Receiver: " + ((SendTransaction)transaction).getReceiver().getName());
                println("Prior: " + prior);
                println("After: " + after);
                printSep();
                emptyLn();
                break;
            case DEPOSIT:
                printSep();
                println("ID: " + id);
                println("Type: DEPOSIT");
                println("Date: " + date);
                println("Amount Deposited: " + amountTransacted);
                println("Prior: " + prior);
                println("After: " + after);
                printSep();
                emptyLn();
                break;
            case WITHDRAW:
                printSep();
                println("ID: " + id);
                println("Type: WITHDRAW");
                println("Date: " + date);
                println("Amount Withdrawn: " + amountTransacted);
                println("Prior: " + prior);
                println("After: " + after);
                printSep();
                emptyLn();
                break;
            case RECEIVE:
                printSep();
                println("ID: " + id);
                println("Type: RECEIVE");
                println("Date: " + date);
                println("Amount Received: " + amountTransacted);
                println("Sender: " + ((ReceiveTransaction)transaction).getSender().getName());
                println("Prior: " + prior);
                println("After: " + after);
                printSep();
                emptyLn();
                break;
        }
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

    private void request(String completion){
        emptyLn();
        printSep();
        println("Please " + completion);
    }

    private void handleError(String completion){
        emptyLn();
        println("Invalid " + completion);
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
