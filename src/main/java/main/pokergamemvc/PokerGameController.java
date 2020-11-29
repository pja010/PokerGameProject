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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.*;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private ImageView PlayerCard1;
    @FXML
    private ImageView PlayerCard2;

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
    @FXML
    private Text playerActionHubText;

    @FXML
    private Text PotAmount;


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

        String filename1 = player.getCard1().getRank() + "_" + player.getCard1().getSuit() + ".png";
        Image image1 = new Image(this.getClass().getResource("/DeckOfCards/" + filename1).toString());
        PlayerCard1.setImage(image1);

        String filename2 = player.getCard2().getRank() + "_" + player.getCard2().getSuit() + ".png";
        Image image2 = new Image(this.getClass().getResource("/DeckOfCards/" + filename2).toString());
        PlayerCard2.setImage(image2);
    }

    public void setTable(Table table) {
        this.table = table;
//        Player player1 = new Player(1);
//        this.table.addPlayer(player1);
//        player1.setPlayerAction(PlayerAction.BET);
//        player1.setBet(20);
//        player1.setUserName("Guillermo");

        PotAmount.setText(String.valueOf(table.getPot().getTotalAmount()));

        System.out.println("Set table");
        table.setBetMin(1);

        updateTable();

    }

    public void updateTable(){

        if (table.getBet() == 1) {
            String filename1 = String.valueOf(table.getTableCards().get(0).getRank()) + "_" + String.valueOf(table.getTableCards().get(0).getSuit()) + ".png";
            Image image1 = new Image(this.getClass().getResource("/DeckOfCards/" + filename1).toString());

            String filename2 = String.valueOf(table.getTableCards().get(1).getRank()) + "_" + String.valueOf(table.getTableCards().get(1).getSuit()) + ".png";
            Image image2 = new Image(this.getClass().getResource("/DeckOfCards/" + filename2).toString());

            String filename3 = String.valueOf(table.getTableCards().get(2).getRank()) + "_" + String.valueOf(table.getTableCards().get(2).getSuit()) + ".png";
            Image image3 = new Image(this.getClass().getResource("/DeckOfCards/" + filename3).toString());


            System.out.println("Flop Cards");
            FlopCard1.setImage(image1);
            FlopCard2.setImage(image2);
            FlopCard3.setImage(image3);
        }
        if(table.getBet() == 2){
            String filename4 = String.valueOf(table.getTableCards().get(3).getRank()) + "_" + String.valueOf(table.getTableCards().get(3).getSuit()) + ".png";
            Image image4 = new Image(this.getClass().getResource("/DeckOfCards/" + filename4).toString());

            TurnCard.setImage(image4);
        }
        if(table.getBet() == 3){
            String filename5 = String.valueOf(table.getTableCards().get(4).getRank()) + "_" + String.valueOf(table.getTableCards().get(4).getSuit()) + ".png";
            Image image5 = new Image(this.getClass().getResource("/DeckOfCards/" + filename5).toString());

            RiverCard.setImage(image5);
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deckOfCards = new Deck();
        deckOfCards.shuffle();
        DeckImageView.setImage(deckOfCards.getBackOfCard());
        System.out.println("Initialize");
    }

    public void updatePlayerActionHub() { // ToDo - perhaps pass player ID num as parameter to only update one player's move at a time
        ArrayList<Player> players = table.getPlayers();
        System.out.println("All players:" + players);
        players.get(0).setPlayerAction(PlayerAction.BET);
        System.out.println("Name is: " + players.get(0).getUserName());
        String playerActionHub = players.get(0).playerActionDescription();
        System.out.println("action: "+playerActionHub);
//        String playerActionHub = this.table.playerActionDescription();
        playerActionHubText.setText(playerActionHub);
    }


    private void updateChipsAmountText() {
        String sChipsAmount = player.getChipsAsString();
        playerChipsAmountText.setText(sChipsAmount);
    }

    public void handleButtonBetAction() {
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
        updateChipsAmountText();
//        updatePlayerActionHub();
    }

    public void handleButtonFoldAction(ActionEvent event) {
        player.makeFoldMove();
//        updatePlayerActionHub();
    }

    public void tieBetTextFieldToEnterButton(ActionEvent event) {
        textFieldUserBetAmount.setOnAction(buttonBet.getOnAction());
    }

    @FXML
    public void nextCardButtonPush() {
        String filename1 = table.getTableCards().get(0).getRank() + "_" + table.getTableCards().get(0).getSuit() + ".png";
        Image image1 = new Image(this.getClass().getResource("/DeckOfCards/" + filename1).toString());
        FlopCard1.setImage(image1);

        //FlopCard2.setImage(deckOfCards.deal().getImage());
        //FlopCard3.setImage(deckOfCards.deal().getImage());

        //TurnCard.setImage(deckOfCards.deal().getImage());
        //RiverCard.setImage(deckOfCards.deal().getImage());
    }


}
