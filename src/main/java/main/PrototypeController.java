package main;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class PrototypeController {
    private PrototypeView theView;
    private PlayerCopy player;

    public PrototypeController(PrototypeView theView, PlayerCopy player) {
        this.player = player;
        this.theView = theView;
        player.moveIsBetPropertyProperty().bind(theView.getBetBtn().defaultButtonProperty());
        player.moveIsCheckMovePropertyProperty().bind(theView.getCheckBtn().defaultButtonProperty());
        player.moveIsFoldPropertyProperty().bind(theView.getFoldBtn().defaultButtonProperty());

        theView.getBetBtn().setOnAction((ActionEvent event) -> {
            try {
                String sUserBetAmount = theView.getTextFieldUserBetAmount().getText();
                if (sUserBetAmount.length() > 0) {
                    double dUserBetAmount = Double.parseDouble(sUserBetAmount);

                    if (dUserBetAmount <= player.getChips())
                    player.makeBetMove(dUserBetAmount);
                }
            }
            catch (NumberFormatException numberFormatException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Illegal input!");
                alert.setHeaderText("Illegal input!");
                alert.setContentText(String.format("Cannot bet \"%s\"",
                                                    theView.getTextFieldUserBetAmount().getText()));
                alert.show();
            }
        });

        theView.getCheckBtn().setOnAction((ActionEvent event) -> {
            player.makeCheckMove();
        });

        theView.getFoldBtn().setOnAction((ActionEvent event) -> {
            player.makeFoldMove();
        });

        // Enable betting and raising by clicking enter button, by tying events to current bet event handler
        theView.getTextFieldUserBetAmount().setOnAction(theView.getBetBtn().getOnAction());
        theView.getRaiseBtn().setOnAction(theView.getBetBtn().getOnAction());
    }
}
