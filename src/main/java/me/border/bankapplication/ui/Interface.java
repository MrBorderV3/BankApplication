package me.border.bankapplication.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import me.border.bankapplication.LoginResponse;
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
        setMainScene();
        window.setTitle("Bank App");
        window.setScene(mainScene);
        window.show();
    }

    private void setMainScene() {
        Label label = new Label("Welcome Customer!");

        Button createButton = new Button("Create an account");
        createButton.setOnAction(e -> openCreateScene());

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> openLoginScene());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            ConfirmBox confirmBox = new  ConfirmBox("Exit Confirmation", "Are you sure you wish to close the application?");
            boolean answer = confirmBox.show();
            if (answer)
                window.close();
        });


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(loginButton, 0, 2);
        GridPane.setConstraints(createButton, 0, 4);
        GridPane.setConstraints(exitButton, 0, 6);

        grid.getChildren().addAll(label, createButton, loginButton, exitButton);

        this.mainScene = new Scene(grid, 250, 170);
        window.setScene(this.mainScene);
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
                Account account = loginResponse.getVault();
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
                Account account = loginResponse.getVault();
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
                Account account = loginResponse.getVault();
                openAccountScene(account);
            } else {
                new AlertBox("Invalid Credentials", "Your password or name are incorrect!").show();
            }
        });

        grid.getChildren().addAll(idLabel, idInput, passwordLabel, passwordInput, loginButton, hideButton, backButton);

        Scene scene = new Scene(grid, 300, 140);
        window.setScene(scene);
    }

    private void openAccountScene(Account account){

    }

    private Button createBackButton(Scene scene){
        Button button = new Button("Back");
        button.setOnAction(e -> window.setScene(scene));

        return button;
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
