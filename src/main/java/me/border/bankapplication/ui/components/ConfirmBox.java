package me.border.bankapplication.ui.components;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    private boolean answer;
    private Stage window;

    public ConfirmBox(String title, String description){
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        Label label = new Label(description);
        label.setAlignment(Pos.TOP_CENTER);

        HBox layout = new HBox(8);
        layout.getChildren().addAll(yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        BorderPane.setAlignment(label, Pos.TOP_CENTER);
        borderPane.setTop(label);
        borderPane.setCenter(layout);


        Scene scene = new Scene(borderPane);
        window.setScene(scene);
    }

    public boolean show(){
        window.showAndWait();

        return answer;
    }
}
