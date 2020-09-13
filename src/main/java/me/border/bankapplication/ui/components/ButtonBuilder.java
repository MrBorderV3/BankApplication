package me.border.bankapplication.ui.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ButtonBuilder {

    private String buttonName;
    private EventHandler<ActionEvent> eventHandler;
    private int[] constraints = new int[2];

    public ButtonBuilder(){
    }

    public ButtonBuilder(String name){
        this.buttonName = name;
    }

    public ButtonBuilder(String name, EventHandler<ActionEvent> value){
        this(name);
        this.eventHandler = value;
    }

    public ButtonBuilder(String name, EventHandler<ActionEvent> value, int column, int row){
        this(name, value);
        constraints[0] = column;
        constraints[1] = row;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public void setOnAction(EventHandler<ActionEvent> value){
        this.eventHandler = value;
    }

    public Button build(){
        Button button = new Button(buttonName);
        button.setOnAction(eventHandler);

        return button;
    }

    public Button buildGrid(){
        Button button = build();
        GridPane.setConstraints(button, constraints[0], constraints[1]);

        return button;
    }
}
