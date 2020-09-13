package me.border.bankapplication.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import me.border.bankapplication.ui.components.ButtonBuilder;
import me.border.bankapplication.utils.LoginResponse;
import me.border.bankapplication.account.Account;
import me.border.bankapplication.account.AccountsManager;
import me.border.bankapplication.ui.components.AlertBox;
import me.border.bankapplication.ui.components.ConfirmBox;

public class Interface extends Application {

    private Stage window;
    private Scene mainScene;

    public Interface() {}

    public void start(String[] args) {
        launch(args);
        //showMainMenu();
    }

    @Override
    public void start(Stage primaryStage) {
        this.window = primaryStage;
        openMainMenu();
    }


    private void openMainMenu() {
        window.setTitle("Bank App");
        // Show a confirmation when the user clicks the (X) button
        window.setOnCloseRequest(e -> {
            ConfirmBox confirmBox = new ConfirmBox("Exit Confirmation", "Are you sure you wish to close the application?");
            boolean answer = confirmBox.show();
            // If they click no, the application will not close and the event will be consumed.
            if (!answer) {
                e.consume();
            } else {
                Platform.exit();
                System.exit(0);
            }
        });

        Label label = new Label("Welcome Customer!");

        Button createButton = new Button("Create an account");
        createButton.setOnAction(e -> openCreateScene());

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> openLoginScene());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            ConfirmBox confirmBox = new ConfirmBox("Exit Confirmation", "Are you sure you wish to close the application?");
            boolean answer = confirmBox.show();
            if (answer)
                window.close();
        });


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(createButton, 0, 2);
        GridPane.setConstraints(loginButton, 0, 4);
        GridPane.setConstraints(exitButton, 0, 7);

        grid.getChildren().addAll(label, createButton, loginButton, exitButton);

        this.mainScene = new Scene(grid, 250, 170);
        window.setScene(mainScene);
        window.show();
    }

    private void openCreateScene(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Full Name:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 3);

        Label cPasswordLabel = new Label("Confirm:");
        GridPane.setConstraints(cPasswordLabel, 0, 5);
        PasswordField cPasswordInput = new PasswordField();
        GridPane.setConstraints(cPasswordInput, 1, 5);

        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 1, 7);

        Button showButton = new Button("Show");
        showButton.setOnAction(e -> {
            String fullName = nameInput.getText();
            String password = passwordInput.getText();
            String confirm = cPasswordInput.getText();
            openAlternateCreateScene(fullName, password, confirm);
        });
        GridPane.setConstraints(showButton, 2, 3);

        Button backButton = createBackButton(mainScene);
        GridPane.setConstraints(backButton, 0, 7);

        createButton.setOnAction(e -> {
            String fullName = nameInput.getText();
            String password = passwordInput.getText();
            String confirm = cPasswordInput.getText();
            if (!password.equals(confirm)){
                new AlertBox("Invalid Credentials", "Passwords do not match!").show();
                return;
            }
            if (AccountsManager.credentialsCheck(fullName, password)){
                Account account = AccountsManager.createAccount(fullName, password);
                openAccountScene(account);
            } else {
                new AlertBox("Invalid Credentials", "Your password or name are invalid!").show();
            }
        });

        grid.getChildren().addAll(usernameLabel, nameInput, passwordLabel, passwordInput, cPasswordLabel, cPasswordInput, createButton, showButton, backButton);

        Scene scene = new Scene(grid, 300, 180);
        window.setScene(scene);
    }

    private void openCreateScene(String name, String password, String confirmation){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Full Name:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField nameInput = new TextField();
        nameInput.setText(name);
        GridPane.setConstraints(nameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setText(password);
        GridPane.setConstraints(passwordInput, 1, 3);

        Label cPasswordLabel = new Label("Confirm:");
        GridPane.setConstraints(cPasswordLabel, 0, 5);
        PasswordField cPasswordInput = new PasswordField();
        cPasswordInput.setText(confirmation);
        GridPane.setConstraints(cPasswordInput, 1, 5);

        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 1, 7);

        Button hideButton = new Button("Hide");
        hideButton.setOnAction(e -> {
            String fullName = nameInput.getText();
            String inputPassword = passwordInput.getText();
            String confirm = cPasswordInput.getText();
            openAlternateCreateScene(fullName, inputPassword, confirm);
        });
        GridPane.setConstraints(hideButton, 2, 3);

        Button backButton = createBackButton(mainScene);
        GridPane.setConstraints(backButton, 0, 7);

        createButton.setOnAction(e -> {
            String fullName = nameInput.getText();
            String inputPassword = passwordInput.getText();
            String confirm = cPasswordInput.getText();
            if (!inputPassword.equals(confirm)){
                new AlertBox("Invalid Credentials", "Passwords do not match!").show();
                return;
            }
            if (AccountsManager.credentialsCheck(fullName, inputPassword)){
                Account account = AccountsManager.createAccount(fullName, inputPassword);
                openAccountScene(account);
            } else {
                new AlertBox("Invalid Credentials", "Your password or name are invalid!").show();
            }
        });

        grid.getChildren().addAll(usernameLabel, nameInput, passwordLabel, passwordInput, cPasswordLabel, cPasswordInput, createButton, hideButton, backButton);

        Scene scene = new Scene(grid, 300, 180);
        window.setScene(scene);
    }

    private void openAlternateCreateScene(String name, String password, String confirmation){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label usernameLabel = new Label("Full Name:");
        GridPane.setConstraints(usernameLabel, 0, 0);
        TextField nameInput = new TextField(name);
        GridPane.setConstraints(nameInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        TextField passwordInput = new TextField();
        passwordInput.setText(password);
        GridPane.setConstraints(passwordInput, 1, 3);

        Label cPasswordLabel = new Label("Confirm:");
        GridPane.setConstraints(cPasswordLabel, 0, 5);
        PasswordField cPasswordInput = new PasswordField();
        cPasswordInput.setText(confirmation);
        GridPane.setConstraints(cPasswordInput, 1, 5);

        Button createButton = new Button("Create");
        GridPane.setConstraints(createButton, 1, 7);

        Button showButton = new Button("Show");
        showButton.setOnAction(e -> {
            String fullName = nameInput.getText();
            String inputPassword = passwordInput.getText();
            String confirm = cPasswordInput.getText();
            openCreateScene(fullName, inputPassword, confirm);
        });
        GridPane.setConstraints(showButton, 2, 3);

        Button backButton = createBackButton(mainScene);
        GridPane.setConstraints(backButton, 0, 7);

        createButton.setOnAction(e -> {
            String fullName = nameInput.getText();
            String inputPassword = passwordInput.getText();
            String confirm = cPasswordInput.getText();
            if (!inputPassword.equals(confirm)){
                new AlertBox("Invalid Credentials", "Passwords do not match!").show();
                return;
            }
            if (AccountsManager.credentialsCheck(fullName, inputPassword)){
                Account account = AccountsManager.createAccount(fullName, inputPassword);
                openAccountScene(account);
            } else {
                new AlertBox("Invalid Credentials", "Your password or name are invalid!").show();
            }
        });

        grid.getChildren().addAll(usernameLabel, nameInput, passwordLabel, passwordInput, cPasswordLabel, cPasswordInput, createButton, showButton, backButton);

        Scene scene = new Scene(grid, 300, 180);

        window.setScene(scene);
    }

    private void openLoginScene(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(8);

        Label idLabel = new Label("Account ID:");
        GridPane.setConstraints(idLabel, 0, 0);
        TextField idInput = new TextField();
        GridPane.setConstraints(idInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 3);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 5);

        Button showButton = new Button("Show");
        showButton.setOnAction(e -> {
            String fullName = idInput.getText();
            String password = passwordInput.getText();
            openAlternateLoginScene(fullName, password);
        });
        GridPane.setConstraints(showButton, 2, 3);


        Button backButton = createBackButton(mainScene);
        GridPane.setConstraints(backButton, 0, 5);

        loginButton.setOnAction(e -> {
            String id = idInput.getText();
            String password = passwordInput.getText();
            LoginResponse loginResponse = AccountsManager.login(id, password);
            if (loginResponse.getAnswer()){
                Account account = loginResponse.getContext();
                openAccountScene(account);
            } else {
                new AlertBox("Invalid Credentials", "Your password or name are incorrect!").show();
            }
        });

        grid.getChildren().addAll(idLabel, idInput, passwordLabel, passwordInput, loginButton, showButton, backButton);

        Scene scene = new Scene(grid, 300, 140);
        window.setScene(scene);
    }

    private void openLoginScene(String id, String password){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(8);

        Label idLabel = new Label("Account ID:");
        GridPane.setConstraints(idLabel, 0, 0);
        TextField idInput = new TextField(id);
        GridPane.setConstraints(idInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setText(password);
        GridPane.setConstraints(passwordInput, 1, 3);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 5);

        Button showButton = new Button("Show");
        showButton.setOnAction(e -> {
            String fullName = idInput.getText();
            String inputPassword = passwordInput.getText();
            openAlternateLoginScene(fullName, inputPassword);
        });
        GridPane.setConstraints(showButton, 2, 3);


        Button backButton = createBackButton(mainScene);
        GridPane.setConstraints(backButton, 0, 5);

        loginButton.setOnAction(e -> {
            String inputId = idInput.getText();
            String inputPassword = passwordInput.getText();
            LoginResponse loginResponse = AccountsManager.login(inputId, inputPassword);
            if (loginResponse.getAnswer()){
                Account account = loginResponse.getContext();
                openAccountScene(account);
            } else {
                new AlertBox("Invalid Credentials", "Your password or name are incorrect!").show();
            }
        });

        grid.getChildren().addAll(idLabel, idInput, passwordLabel, passwordInput, loginButton, showButton, backButton);

        Scene scene = new Scene(grid, 300, 140);
        window.setScene(scene);
    }

    private void openAlternateLoginScene(String id, String password){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(8);

        Label idLabel = new Label("Account ID:");
        GridPane.setConstraints(idLabel, 0, 0);
        TextField idInput = new TextField(id);
        GridPane.setConstraints(idInput, 1, 0);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 3);
        TextField passwordInput = new TextField(password);
        GridPane.setConstraints(passwordInput, 1, 3);

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 5);

        Button hideButton = new Button("Hide");
        hideButton.setOnAction(e -> {
            String fullName = idInput.getText();
            String inputPassword = passwordInput.getText();
            openLoginScene(fullName, inputPassword);
        });
        GridPane.setConstraints(hideButton, 2, 3);


        Button backButton = createBackButton(mainScene);
        GridPane.setConstraints(backButton, 0, 5);

        loginButton.setOnAction(e -> {
            String inputId = idInput.getText();
            String inputPassword = passwordInput.getText();
            LoginResponse loginResponse = AccountsManager.login(inputId, inputPassword);
            if (loginResponse.getAnswer()){
                Account account = loginResponse.getContext();
                openAccountScene(account);
            } else {
                new AlertBox("Invalid Credentials", "Your password or name are incorrect!").show();
            }
        });

        grid.getChildren().addAll(idLabel, idInput, passwordLabel, passwordInput, loginButton, hideButton, backButton);

        Scene scene = new Scene(grid, 300, 140);
        window.setScene(scene);
    }

    private void openAccountScene(Account account) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(12);
        grid.setHgap(10);

        Button balanceBut = new ButtonBuilder("My Balance", e -> openBalanceScene(account), 0, 0).buildGrid();
        Button logoutBut = new ButtonBuilder("Log Out", e -> openMainMenu(), 0, 7).buildGrid();
        Button depositButton = new ButtonBuilder("Deposit", e -> openDepositMenu(account), 0, 1).buildGrid();
        Button withdrawButton = new ButtonBuilder("Withdraw", e -> openWithdrawMenu(account), 0, 2).buildGrid();

        grid.getChildren().addAll(balanceBut, depositButton, withdrawButton, logoutBut);

        Scene scene = new Scene(grid, 300, 200);

        window.setScene(scene);
    }

    private void openWithdrawMenu(Account account){
        GridPane grid = getBaseActionGrid();

        Label label = new Label("Withdraw Amount:");
        GridPane.setConstraints(label, 0, 0);
        TextField amount = new TextField();
        GridPane.setConstraints(amount, 2, 0);

        Button withdrawButton = new ButtonBuilder("Withdraw", e -> {
            try {
                double dAmount = Double.parseDouble(amount.getText());
                ConfirmBox confirmBox = new ConfirmBox("Withdraw Confirmation", "Are you sure you want to withdraw $" + dAmount + " to your account?");
                if (confirmBox.show()) {
                    if (account.withdraw(dAmount)) {
                        new AlertBox("Success", "You have successfully withdrawn $" + dAmount + " from your account").show();
                        openAccountScene(account);
                    } else {
                        new AlertBox("Invalid Amount", "You do not have enough balance to withdraw this amount.").show();
                    }
                }
            } catch (NumberFormatException ex){
                new AlertBox("Invalid Number", "Invalid number! Please try again.").show();
            }
        }, 2, 3).buildGrid();

        Button backButton = createAccountBackButton(account);
        GridPane.setConstraints(backButton, 0, 3);

        grid.getChildren().addAll(label, amount, withdrawButton, backButton);

        Scene scene = new Scene(grid, 310, 90);
        window.setScene(scene);
    }

    private void openDepositMenu(Account account){
        GridPane grid = getBaseActionGrid();

        Label label = new Label("Deposit Amount:");
        GridPane.setConstraints(label, 0, 0);
        TextField amount = new TextField();
        GridPane.setConstraints(amount, 2, 0);

        Button depositButton = new ButtonBuilder("Deposit", e -> {
            try {
                double dAmount = Double.parseDouble(amount.getText());
                ConfirmBox confirmBox = new ConfirmBox("Deposit Confirmation", "Are you sure you want to deposit $" + dAmount + " to your account?");
                if (confirmBox.show()) {
                    if (account.deposit(dAmount)) {
                        new AlertBox("Success", "You have successfully deposited $" + dAmount + " to your account").show();
                        openAccountScene(account);
                    } else {
                        new AlertBox("Invalid Amount", "You cannot deposit this amount! please try again.").show();
                    }
                }
            } catch (NumberFormatException ex){
                new AlertBox("Invalid Number", "Invalid number! Please try again.").show();
            }
        }, 2, 3).buildGrid();

        Button backButton = createAccountBackButton(account);
        GridPane.setConstraints(backButton, 0, 3);

        grid.getChildren().addAll(label, amount, depositButton, backButton);

        Scene scene = new Scene(grid, 280, 90);
        window.setScene(scene);
    }

    private void openBalanceScene(Account account){
        double balance = account.getBalance();
        GridPane grid = getBaseActionGrid();

        Label label = new Label("Your Balance: $" + balance);
        GridPane.setConstraints(label, 1, 2);

        Button backButton = createBackButton(() -> openAccountScene(account));
        GridPane.setConstraints(backButton, 0, 3);
        grid.getChildren().addAll(label, backButton);


        Scene scene = new Scene(grid, 225, 80);
        window.setScene(scene);
    }

    private GridPane getBaseActionGrid(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(6);
        grid.setVgap(8);

        return grid;
    }

    private Button createBackButton(Scene scene){
        return new ButtonBuilder("Back", e -> window.setScene(scene)).build();
    }

    private Button createBackButton(Runnable runnable){
        return new ButtonBuilder("Back", e -> runnable.run()).build();
    }

    private Button createAccountBackButton(Account account){
        return createBackButton(() -> openAccountScene(account));
    }

    /*private void showLoginMenu(Account account) {
        char option = '\0';
        Scanner optionScanner = new Scanner(System.in);
        displayLogin(account);

        do {
            String scan = optionScanner.next();
            if (scan.length() != 1) {
                handleError("option! Please enter a new option.");
                continue;
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
                    if (receiverAccount != null) {
                        Scanner sendAmountScanner = new Scanner(System.in);
                        request("enter the amount of money you'd like to send to " + receiverAccount.getName());
                        double sAmount;
                        try {
                            sAmount = sendAmountScanner.nextDouble();
                        } catch (Exception e) {
                            handleError("amount");
                            break;
                        }

                        if (account.send(receiverAccount, sAmount)) {
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
                    break;
                case 'e':
                case 'E':
                    if (account.getTransactions().isEmpty()) {
                        emptyLn();
                        println("You do not have any transactions yet!");
                        break;
                    }
                    showTransactionsMenu(account);
                    break;
                case 'f':
                case 'F':
                    emptyLn();
                    printSep();
                    println("Name: " + account.getName());
                    println("ID: " + account.getID());
                    println("Balance: " + account.getBalance());
                    println("Amount of Transactions: " + account.getTransactions().size());
                    printSep();
                    emptyLn();
                    break;
                default:
                    if (option != 'G' && option != 'g')
                        handleError("option! Please enter a different option.");
            }
        } while (option != 'G' && option != 'g');
        println("Thank you for using our services! You will now be logged out of your account");
    }

    private void showTransactionsMenu(Account account) {
        char tOption = '\0';
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
                continue;
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

    public void printTransaction(Transaction transaction, int id) {
        TransactionType type = transaction.getType();
        String date = transaction.getDate();
        double prior = transaction.getPrior();
        double after = transaction.getAfter();
        double amountTransacted = transaction.getTransactionAmount();
        switch (type) {
            case SEND:
                printSep();
                println("ID: " + id);
                println("Type: SEND");
                println("Date: " + date);
                println("Amount Sent: " + amountTransacted);
                println("Receiver: " + ((SendTransaction) transaction).getReceiver().getName());
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
                println("Sender: " + ((ReceiveTransaction) transaction).getSender().getName());
                println("Prior: " + prior);
                println("After: " + after);
                printSep();
                emptyLn();
                break;
        }
    }

    private void displayLogin(Account account) {
        println("Welcome back " + account.getName() + "!");
        printSep();
        emptyLn();
        println("A. My Balance");
        println("B. Deposit");
        println("C. Withdraw");
        println("D. Send");
        println("E. Transaction History");
        println("F. Account Details");
        println("G. Logout");
    }

    private void displayStartup() {
        printSep();
        println("Welcome Customer");
        printSep();
        emptyLn();
        println("A. Login");
        println("B. Create an account");
        println("C. Exit");
    }

    private void request(String completion) {
        emptyLn();
        printSep();
        println("Please " + completion);
    }

    private void handleError(String completion) {
        emptyLn();
        println("Invalid " + completion);
    }

    private void emptyLn() {
        println("");
    }

    private void printSep() {
        println("-------------------------------------------");
    }

    private void println(String string) {
        System.out.println(string);
    }*/
}
