package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import static main.PlayerAction.*;

public class PrototypeController {
    private PrototypeModel theModel;
    private PrototypeView theView;
//    private static Player player;

    public PrototypeController(PrototypeModel theModel, PrototypeView theView, Player player) {
        this.theModel = theModel;
        this.theView = theView;

//        Player player = new Player(1);

        theView.getBetBtn().setOnAction((ActionEvent event) -> {
            try {
                String sUserBetAmount = theView.getTextFieldUserBetAmount().getText();
                if (sUserBetAmount.length() > 0) {
                    double dUserBetAmount = Double.parseDouble(sUserBetAmount);
                    System.out.println("Player amount before bet: " + player.getChips());
                    player.move(BET, dUserBetAmount);
                    System.out.println("Player amount after bet: " + player.getChips());
                }
            }
            catch (NumberFormatException numberFormatException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Illegal input!");
                alert.setHeaderText("Illegal input specified.");
                alert.setContentText(String.format("Can not convert \"%s\"",
                                                    theView.getTextFieldUserBetAmount().getText()));
                alert.show();
            }
        });

        theView.getCheckBtn().setOnAction((ActionEvent event) -> {
//            player.move(CHECK);
            // ToDo - fix check move
        });

        theView.getFoldBtn().setOnAction((ActionEvent event) -> {
//            player.move(FOLD, 0);
            // ToDo - fix fold move
            player.isPlaying = false;
        });

        // Enable betting and raising by clicking enter button, by tying events to current bet event handler
        theView.getTextFieldUserBetAmount().setOnAction(theView.getBetBtn().getOnAction());
        theView.getRaiseBtn().setOnAction(theView.getBetBtn().getOnAction());
    }
}
