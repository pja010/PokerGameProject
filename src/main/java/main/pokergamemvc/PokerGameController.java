/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Per Ästrom, Lindsay Knupp, Callie Valenti, Guillermo Torres
 * Section: 02-8:50 am and 01-11:30 am
 * Date: 11/2/20
 * Time: 11:46 AM
 *
 * Project: csci205FinalProject
 * Package: main.PokerGameVisuals
 * Class: PokerGameController
 *
 * Description:
 *
 * ****************************************
 */
package main.pokergamemvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Deck;
import main.PlayerCopy;
import main.Table;

import java.net.URL;
import java.util.ResourceBundle;

public class PokerGameController implements Initializable {

    @FXML
    private ImageView DeckImageView;
    @FXML
    private HBox FlopCardsView;
    @FXML
    private Button nextCardButton;
    @FXML
    private ImageView FlopCard1;
    @FXML
    private ImageView FlopCard2;
    @FXML
    private ImageView FlopCard3;
    @FXML
    private ImageView TurnCard;
    @FXML
    private ImageView RiverCard;

    private Deck deckOfCards;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text playerChipsAmountText;

    @FXML
    private Button buttonBet;

    @FXML
    private Button buttonCheck;

    @FXML
    private Button buttonFold;

    @FXML
    private TextField textFieldUserBetAmount;

    // The model for this view
//    private PokerGameModel theModel;
    private PlayerCopy player;

    private Table table;

    public PokerGameController() {
    }

    @FXML
    void initialize() {
        assert buttonBet != null : "fx:id=\"buttonBet\" was not injected: check your FXML file 'PokerGameView.fxml'.";
        assert buttonCheck != null : "fx:id=\"buttonCheck\" was not injected: check your FXML file 'PokerGameView.fxml'.";
    }

    public void setPlayer(PlayerCopy player) {
        this.player = player;
        System.out.println("Set player");
        player.moveIsBetPropertyProperty().bind(buttonBet.defaultButtonProperty());
        player.moveIsCheckMovePropertyProperty().bind(buttonCheck.defaultButtonProperty());
        player.moveIsFoldPropertyProperty().bind(buttonFold.defaultButtonProperty());
    }

    public void setTable(Table table){
        this.table = table;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deckOfCards = new Deck();
        deckOfCards.shuffle();
        DeckImageView.setImage(deckOfCards.getBackOfCard());
        System.out.println("Initialize");
    }

    public void handleButtonBetAction(ActionEvent event) {
        try {
            String sUserBetAmount = textFieldUserBetAmount.getText();
            if (sUserBetAmount.length() > 0) {
                double dUserBetAmount = Double.parseDouble(sUserBetAmount);

                if (dUserBetAmount <= player.getChips() && dUserBetAmount >= table.getBetMin())
                    player.makeBetMove(dUserBetAmount);
            }
        }
        catch (NumberFormatException numberFormatException) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Illegal input!");
            alert.setHeaderText("Illegal input!");
            alert.setContentText(String.format("Cannot bet \"%s\"",
                    textFieldUserBetAmount.getText()));
            alert.show();
        }
    }

    public void handleButtonCheckAction(ActionEvent event) {
        player.makeCheckMove();
        playerChipsAmountText.setText("Chips amount: $" + player.getChipsAsString());

    }

    public void handleButtonFoldAction(ActionEvent event) {
        player.makeFoldMove();
    }

    public void tieBetTextFieldToEnterButton(ActionEvent event) {
        textFieldUserBetAmount.setOnAction(buttonBet.getOnAction());
    }

    @FXML
    public void nextCardButtonPush() {
        //FlopCard1.setImage(deckOfCards.deal().getImage());
        //FlopCard2.setImage(deckOfCards.deal().getImage());
        //FlopCard3.setImage(deckOfCards.deal().getImage());

        //TurnCard.setImage(deckOfCards.deal().getImage());
        //RiverCard.setImage(deckOfCards.deal().getImage());
    }


}
