/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Per Astrom, Lindsay Knupp, Callie Valenti, Guillermo Torres
 * Section: 02-8:50 am and 01-11:30 am
 * Date: 11/2/20
 * Time: 11:46 AM
 *
 * Project: csci205FinalProject
 * Package: main.PokerGameVisuals
 * Class: PokerGameController
 *
 * Description: Controller class for the
 * Poker game GUI.
 *
 * ****************************************
 */
package main.pokergamemvc;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.Deck;
import main.Player;
import main.GUIPlayer;
import main.Table;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that binds PokerGameView.java, an FXML file,
 * with the models PlayerCopy.java and Table.java.
 */
public class PokerGameController implements Initializable {

    public Text playerActionHubText1;
    public Text playerActionHubText2;
    public Text playerActionHubText4;
    public Text playerActionHubText3;
    @FXML
    private ImageView DeckImageView;
    @FXML
    private HBox FlopCardsView;
    @FXML
    private Button nextCardButton;

    /**
     * png images for the cards on the table and the player's hand.
     */
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

    /**
     * The player action buttons and corresponding texts.
     */
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

    @FXML
    private TextArea instructions;

    /**
     * The models for the view.
     */
    private GUIPlayer player;
    private Table table;

    /**
     * Text to display all player's actions on the table.
     */
    @FXML
    private Text playerActionHubText;

    @FXML
    private Text minBet;

    @FXML
    private Text playerTurnText;

    @FXML
    private Text PotAmount;

    /**
     *  Default constructor.
     */
    public PokerGameController() {
    }

    /**
     * Initialize the buttons from the FXML view.
     */
    @FXML
    void initialize() {
        assert buttonBet != null : "fx:id=\"buttonBet\" was not injected: check your FXML file 'PokerGameView.fxml'.";
        assert buttonCheck != null : "fx:id=\"buttonCheck\" was not injected: check your FXML file 'PokerGameView.fxml'.";
    }

    /**
     * Set the player model.
     * @param player the player model
     */
    public void setPlayer(GUIPlayer player) {
        this.player = player;
        //System.out.println("Set player");
        player.moveIsBetPropertyProperty().bind(buttonBet.defaultButtonProperty());
        player.moveIsCheckMovePropertyProperty().bind(buttonCheck.defaultButtonProperty());
        player.moveIsFoldPropertyProperty().bind(buttonFold.defaultButtonProperty());

        updatePlayerCards();
        updateChipsAmountText();
    }

    public void updatePlayerCards(){
        String filename1 = player.getCard1().getRank() + "_" + player.getCard1().getSuit() + ".png";
        Image image1 = new Image(this.getClass().getResource("/DeckOfCards/" + filename1).toString());
        PlayerCard1.setImage(image1);

        String filename2 = player.getCard2().getRank() + "_" + player.getCard2().getSuit() + ".png";
        Image image2 = new Image(this.getClass().getResource("/DeckOfCards/" + filename2).toString());
        PlayerCard2.setImage(image2);
    }

    /**
     * Set the table model.
     * @param table the table model.
     */
    public void setTable(Table table) {
        this.table = table;
        //System.out.println("Set table");
        PotAmount.setText(String.valueOf(table.getPot().getTotalAmount()));

        updateTable();
        playerTurnText.setText(table.getPlayerTurnMessage());
        updatePlayerActionHub();

        minBet.setText("Min Bet: " + table.getBetMin());

    }

    public void updateTable(){

        if (table.getBet() == 0){
            FlopCard1.setImage(null);
            FlopCard2.setImage(null);
            FlopCard3.setImage(null);
            TurnCard.setImage(null);
            RiverCard.setImage(null);
        }

        if (table.getBet() == 1) {
            String filename1 = String.valueOf(table.getTableCards().get(0).getRank()) + "_" + String.valueOf(table.getTableCards().get(0).getSuit()) + ".png";
            Image image1 = new Image(this.getClass().getResource("/DeckOfCards/" + filename1).toString());

            String filename2 = String.valueOf(table.getTableCards().get(1).getRank()) + "_" + String.valueOf(table.getTableCards().get(1).getSuit()) + ".png";
            Image image2 = new Image(this.getClass().getResource("/DeckOfCards/" + filename2).toString());

            String filename3 = String.valueOf(table.getTableCards().get(2).getRank()) + "_" + String.valueOf(table.getTableCards().get(2).getSuit()) + ".png";
            Image image3 = new Image(this.getClass().getResource("/DeckOfCards/" + filename3).toString());


            //System.out.println("Flop Cards");
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

    /**
     * Initialize the card deck, shuffle it and provide an image.
     * @param location the location of the png image.
     * @param resources the image resource bundle.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //System.out.println("Initialize");
    }

    /**
     * Updates the text display on the view for players' actions.
     */
    public void updatePlayerActionHub() {
//        String playerActionHub = table.getPlayerActionText();
//        playerActionHubText1.setText(playerActionHub);
        playerActionHubText1.setText(table.getPlayerActionTexts().get(0));
        playerActionHubText2.setText(table.getPlayerActionTexts().get(1));
        playerActionHubText3.setText(table.getPlayerActionTexts().get(2));
        playerActionHubText4.setText(table.getPlayerActionTexts().get(3));
    }

    /**
     * Sends a description of the player's action to the table.
     * @param //textToDisplay the action description.
     */
    //public void passPlayerActionTextToTable(String textToDisplay) {
    //    table.setPlayerActionText();
    //}

    public void passPlayerNameToTable(String playerName, int playerID) {
        for (Player player: table.getPlayers() ) {
            if (player.getPlayerNum() == playerID) {
                player.setUserName(playerName);
            }
        }
    }

    public void writePlayerTurnMessage(int playerNum) {
        Player thisPlayer = table.getPlayers().get(playerNum);
        String turnMessage = thisPlayer.getUserName() + "'s turn.";
        table.setPlayerTurnMessage(turnMessage);
        updatePlayerTurnText();
    }

    /**
     * Visualizes which player is choosing his/herself bets
     */
    public void updatePlayerTurnText() {
        playerTurnText.setText(table.getPlayerTurnMessage());
    }

    /**
     * Updates the player's amount of chips on the view.
     */
    public void updateChipsAmountText() {
        String sChipsAmount = player.getChipsAsString();
        playerChipsAmountText.setText(sChipsAmount);
    }

    /**
     * Handles a bet action.
     * Catches @NumberFormatException if input is not a number.
     */
    public void handleButtonBetAction() {
        try {
            String sUserBetAmount = textFieldUserBetAmount.getText();
            if (sUserBetAmount.length() > 0) {
                double dUserBetAmount = Double.parseDouble(sUserBetAmount);

                if (dUserBetAmount <= player.getChips() && dUserBetAmount >= table.getBetMin()) {
                    player.makeBetMove(dUserBetAmount);
                    updateChipsAmountText();
                    //passPlayerActionTextToTable(player.playerActionDescription());
                    updatePlayerActionHub();
                }
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

    /**
     * Handles a check action.
     */
    public void handleButtonCheckAction() {
        player.makeCheckMove();
        playerChipsAmountText.setText("Chips amount: $" + player.getChipsAsString());
        updateChipsAmountText();
        //passPlayerActionTextToTable(player.playerActionDescription());
        updatePlayerActionHub();
    }

    /**
     * Handles a fold action.
     */
    public void handleButtonFoldAction() {
        player.makeFoldMove();
        //passPlayerActionTextToTable(player.playerActionDescription());
        updatePlayerActionHub();
    }

    /**
     * Ties the keyboard enter button to the bet action button.
     */
    public void tieBetTextFieldToEnterButton() {
        textFieldUserBetAmount.setOnAction(buttonBet.getOnAction());
    }

    /**
     * Visualizes the table cards when button is clicked
     */
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
