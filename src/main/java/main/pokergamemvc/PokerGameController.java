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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import main.Deck;

public class PokerGameController implements Initializable {

    @FXML
    private ImageView DeckImageView;
    @FXML
    private HBox FlopCardsView;

    @FXML
    private Button nextCardButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonBet;

    @FXML
    private Button buttonCheck;

    // The model for this view
    private PokerGameModel theModel;

    public PokerGameController() {
    }

    @FXML
    void onMouseClickedBet(MouseEvent event) {
        System.out.println("Bet clicked");

    }

    @FXML
    void onMouseClickedCheck(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert buttonBet != null : "fx:id=\"buttonBet\" was not injected: check your FXML file 'PokerGameView.fxml'.";
        assert buttonCheck != null : "fx:id=\"buttonCheck\" was not injected: check your FXML file 'PokerGameView.fxml'.";

    }

    void setModel(PokerGameModel theModel) {
        this.theModel = theModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Deck deckOfCards = new Deck();
        DeckImageView.setImage(deckOfCards.getBackOfCard());
    }
}
