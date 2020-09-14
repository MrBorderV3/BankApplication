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
import me.border.bankapplication.transaction.*;
import me.border.utilities.ui.ButtonBuilder;
import me.border.bankapplication.account.Account;
import me.border.bankapplication.account.AccountsManager;
import me.border.utilities.ui.AlertBox;
import me.border.utilities.ui.ConfirmBox;
import me.border.utilities.ui.FieldBox;
import me.border.utilities.utils.ImmuteableResponse;

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
            if (answer) {
                Platform.exit();
                System.exit(0);
            }
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
            ImmuteableResponse<Account> loginResponse = AccountsManager.login(id, password);
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
            ImmuteableResponse<Account> loginResponse = AccountsManager.login(inputId, inputPassword);
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
            ImmuteableResponse<Account> loginResponse = AccountsManager.login(inputId, inputPassword);
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
        Button sendButton = new ButtonBuilder("Send", e -> openSendMenu(account), 0, 3).buildGrid();
        Button transactionsButton = new ButtonBuilder("Transactions", e -> {
            if (account.getTransactions().isEmpty()){
                new AlertBox("No Transactions", "Your account currently has no transactions so you may not open this tab!").show();
            } else {
                openTransactionsMenu(account);
            }
        }, 0, 4).buildGrid();
        Button accountButton = new ButtonBuilder("Account", e -> openAccountDetailsScene(account), 0, 5).buildGrid();

        grid.getChildren().addAll(balanceBut, depositButton, withdrawButton, sendButton, transactionsButton, accountButton, logoutBut);

        Scene scene = new Scene(grid, 300, 280);

        window.setScene(scene);
    }

    private void openAccountDetailsScene(Account account){
        GridPane grid = getBaseActionGrid();

        Button backButton = createAccountBackButton(account);
        GridPane.setConstraints(backButton, 0, 4);

        Label id = new Label("ID: " + account.getID());
        GridPane.setConstraints(id, 1, 0);

        Label balance = new Label("Balance: " + account.getBalance());
        GridPane.setConstraints(balance, 1, 1);

        Label amountOfT = new Label("Amount Of Transactions: " + account.getTransactions().size());
        GridPane.setConstraints(amountOfT, 1, 2);

        grid.getChildren().addAll(id, balance, amountOfT, backButton);
        Scene scene = new Scene(grid, 215, 125);
        window.setScene(scene);
    }

    private void openTransactionsMenu(Account account){
        GridPane grid = getBaseActionGrid();
        grid.setVgap(12);
        grid.setHgap(10);

        Button fullTransactions = new ButtonBuilder("Transaction History", e -> printTransaction(account, 1), 0, 0).buildGrid();
        GridPane.setConstraints(fullTransactions, 0, 0);

        Button previousTransaction = new ButtonBuilder("Previous Transaction", e -> printTransaction(account, account.getTransactions().size()), 0, 1).buildGrid();

        Button chooseTransaction = new ButtonBuilder("Choose A Transaction", e -> {
            String option = new FieldBox("Choose a Transaction", "Transaction ID:").show();
            try {
                int id = Integer.parseInt(option);
                if (id > account.getTransactions().size() || id < 1){
                    new AlertBox("Invalid Option", "Invalid option! Please try again!").show();
                } else {
                    printTransaction(account, id);
                }
            } catch (NumberFormatException ex){
                new AlertBox("Invalid Option", "Invalid option! Please try again!").show();
            }
        }, 0, 2).buildGrid();

        Button backButton = createAccountBackButton(account);
        GridPane.setConstraints(backButton, 0, 4);

        grid.getChildren().addAll(fullTransactions, previousTransaction, chooseTransaction, backButton);

        Scene scene = new Scene(grid, 230, 170);
        window.setScene(scene);
    }

    public void printTransaction(Account account, int id) {
        Transaction transaction = account.getTransactions().get(id-1);
        GridPane grid = getBaseActionGrid();

        TransactionType type = transaction.getType();
        String date = transaction.getDate();
        double prior = transaction.getPrior();
        double after = transaction.getAfter();
        double amountTransacted = transaction.getTransactionAmount();

        Label idLabel = new Label("ID: " + id);
        GridPane.setConstraints(idLabel, 1, 0);

        Label typeLabel = new Label("Type: " + type.name());
        GridPane.setConstraints(typeLabel, 1, 1);

        Label dateLabel = new Label("Date: " + date);
        GridPane.setConstraints(dateLabel, 1, 2);

        grid.getChildren().addAll(idLabel, typeLabel, dateLabel);
        boolean addOne = false;
        switch (type) {
            case SEND:
                Label sentLabel = new Label("Amount Sent: " + amountTransacted);
                GridPane.setConstraints(sentLabel, 1, 3);

                Label receiverLabel = new Label("Receiver: " + ((TWTransaction) transaction).getParty().getName());
                GridPane.setConstraints(receiverLabel, 1, 4);

                grid.getChildren().addAll(sentLabel, receiverLabel);
                addOne = true;
                break;
            case DEPOSIT:
                Label depositedLabel = new Label("Amount Deposited: " + amountTransacted);
                GridPane.setConstraints(depositedLabel, 1, 3);

                grid.getChildren().add(depositedLabel);
                break;
            case WITHDRAW:
                Label withdrawnLabel = new Label("Amount Withdrawn: " + amountTransacted);
                GridPane.setConstraints(withdrawnLabel, 1, 3);

                grid.getChildren().add(withdrawnLabel);
                break;
            case RECEIVE:
                Label receivedLabel = new Label("Amount Received: " + amountTransacted);
                GridPane.setConstraints(receivedLabel, 1, 3);

                Label senderLabel = new Label("Sender: " + ((TWTransaction) transaction).getParty().getName());
                GridPane.setConstraints(senderLabel, 1, 4);

                grid.getChildren().addAll(receivedLabel, senderLabel);
                addOne = true;
                break;
        }
        Label priorLabel = new Label("Prior: " + prior);
        Label afterLabel = new Label("After: " + after);
        if (addOne){
            GridPane.setConstraints(priorLabel, 1, 5);
            GridPane.setConstraints(afterLabel, 1, 6);
        } else {
            GridPane.setConstraints(priorLabel, 1, 4);
            GridPane.setConstraints(afterLabel, 1, 5);
        }

        Button backBut = createBackButton(() -> openTransactionsMenu(account));
        GridPane.setConstraints(backBut, 0, 8);

        Button previousBut = new ButtonBuilder("Previous", e -> {
            if (id == 1){
                new AlertBox("Invalid Transaction", "This is the first transaction on your account, there is no previous transaction!").show();
            } else {
                printTransaction(account, id-1);
            }
        }, 1, 8).buildGrid();

        Button nextBut = new ButtonBuilder("Next", e -> {
            if (account.getTransactions().size() == id){
                new AlertBox("Invalid Transaction", "This is the last transaction on your account, there is no next transaction!").show();
            } else {
                printTransaction(account, id+1);
            }
        }, 3, 8).buildGrid();

        grid.getChildren().addAll(priorLabel, afterLabel, backBut);
        if (id != 1){
            grid.getChildren().add(previousBut);
        }
        if (account.getTransactions().size() != id){
            grid.getChildren().addAll(nextBut);
        }

        if (transaction instanceof TWTransaction) {
            Scene scene = new Scene(grid, 315, 225);
            window.setScene(scene);
        } else {
            Scene scene = new Scene(grid, 315, 210);
            window.setScene(scene);
        }
    }

    private void openSendMenu(Account account){
        GridPane grid = getBaseActionGrid();

        Label idLabel = new Label("ID:");
        GridPane.setConstraints(idLabel, 0, 0);
        TextField idField = new TextField();
        GridPane.setConstraints(idField, 2, 0);

        Label amountLabel = new Label("Amount:");
        GridPane.setConstraints(amountLabel, 0, 1);
        TextField amountField = new TextField();
        GridPane.setConstraints(amountField, 2, 1);

        Button sendButton = new ButtonBuilder("Send", e -> {
            try {
                double dAmount = Double.parseDouble(amountField.getText());
                String id = idField.getText();
                ConfirmBox confirmBox = new ConfirmBox("Send Confirmation", "Are you sure you want to send $" + dAmount + " to the account " + id + "?");
                if (confirmBox.show()) {
                    ImmuteableResponse<Account> sendResponse = AccountsManager.getAccount(id);
                    if (sendResponse.getAnswer()) {
                        Account receiver = sendResponse.getContext();
                        if (account.send(receiver, dAmount)) {
                            new AlertBox("Success", "You have successfully sent $" + dAmount + " to " + receiver.getName() + "!").show();
                            openAccountScene(account);
                        } else {
                            new AlertBox("Invalid Amount", "You do not have enough balance to send this amount.").show();
                        }
                    } else {
                        new AlertBox("Invalid Account", "The account ID entered does not exist").show();
                    }
                }
            } catch (NumberFormatException ex){
                new AlertBox("Invalid Number", "Invalid number! Please try again.").show();
            }
        }, 2, 3).buildGrid();

        Button backButton = createAccountBackButton(account);
        GridPane.setConstraints(backButton, 0, 3);

        grid.getChildren().addAll(idLabel, idField, amountLabel, amountField, sendButton, backButton);

        Scene scene = new Scene(grid, 310, 110);
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
}
