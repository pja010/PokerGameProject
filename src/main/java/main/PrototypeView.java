package main;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import main.PrototypeModel;

public class PrototypeView {

    private PrototypeModel theModel;
    private final Button betBtn;
    private final StackPane root;
    private final TextField textFieldUserBetAmount;
    private final Button checkBtn;
    private final Button foldBtn;
    private final Button raiseBtn;

    public PrototypeView(PrototypeModel theModel) {
        this.theModel = theModel;

        root = new StackPane();
        root.setPrefWidth(1000);
        root.setPrefHeight(300);

        HBox topPane = new HBox(10);
        root.getChildren().add(topPane);

        textFieldUserBetAmount = new TextField();
        topPane.getChildren().add(new Label("Betting amount: "));
        topPane.getChildren().add(textFieldUserBetAmount);

        betBtn = new Button("BET");;
        checkBtn = new Button("CHECK");
        foldBtn = new Button("FOLD");
        raiseBtn = new Button("RAISE");

        // todo - Include raise button only if not first move, or have bet/raise button
        topPane.getChildren().addAll(betBtn, checkBtn, foldBtn, raiseBtn);
    }

    public Button getBetBtn() {
        return betBtn;
    }

    public Button getCheckBtn() {
        return checkBtn;
    }

    public Button getFoldBtn() {
        return foldBtn;
    }

    public Button getRaiseBtn() {
        return raiseBtn;
    }

    public TextField getTextFieldUserBetAmount() {
        return textFieldUserBetAmount;
    }

    public StackPane getRoot() {
        return root;
    }
}


