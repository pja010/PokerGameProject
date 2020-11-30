/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/2/20
 * Time: 11:59 AM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Player
 *
 * Description:
 * A player object for the poker game.
 *
 * ****************************************
 */
package main;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that models a player in a game of poker, excluding properties.
 */
public class Player implements Serializable {

    /**
     * The player's game identification numbers.
     */
    private int playerNum;

    /**
     * Whether or not the player is the dealer.
     */
    private boolean isDealer;

    /**
     * The player's hand represented as a list containing Card objects.
     */
    public ArrayList<Card> playerHand = new ArrayList<Card>();
    private Card card1;
    private Card card2;
    private Chips chips;

    /**
     * The score of the player's hand.
     */
    private ScoreUpdate score;

    /**
     * The amount of the player's bet.
     */
    private double bet;

    /**
     * The player's choice of move.
     */
    public boolean isPlaying;

    /**
     * The player's user name.
     * It will be displayed to other players in the game.
     */
    public String userName;

    /**
     * Boolean to determine whether player is still active in round.
     */
    private ArrayList<Boolean> isRoundDone;

    /**
     * The player's choice of move.
     */
    public PlayerAction playerAction;

    /**
     * Constructor initializes the player's fields.
     * @param playerNum the player's identification number.
     */
    public Player(int playerNum) {
        this.playerNum = playerNum;
        this.chips = new Chips();
        this.isPlaying = true;
        this.bet = 0;
        this.playerAction = null;
        this.isRoundDone = new ArrayList<>();
        this.isRoundDone.add(false);
        this.isRoundDone.add(false);
        this.isRoundDone.add(false);
        this.isRoundDone.add(false);

    }

    /**
     * Sets the player's amount of chips to a specified number.
     * @param initAmount the initial number of chips.
     */
    public void setChips(double initAmount) {
        this.chips.initAmount = initAmount;
        this.chips.currAmount = initAmount;
    }

    /**
     * Sets the first card in the player's hand.
     * @param card1 the type of card to be set.
     */
    public void setCard1(Card card1){
        this.card1 = card1;
        this.playerHand.add(card1);
    }

    /**
     * Sets the second card in the player's hand.
     * @param card2 the type of card to be set.
     */
    public void setCard2(Card card2){
        this.card2 = card2;
        this.playerHand.add(card2);
    }

    /**
     * Adds a specified card to the player's hand.
     * @param card the card to be added.
     */
    public void addCard(Card card) {
        this.playerHand.add(card);
    }

    /**
     * Gets the total score of the player's current hand.
     * @return the total score.
     */
    public ScoreUpdate getScore() {
        score = new ScoreUpdate(playerHand);
        return score;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    public void addChips(double amount){
        chips.currAmount += amount;
    }

    public void subChips(double amount){
        chips.currAmount -= amount;
    }

    public Chips getChips(){
        return chips;
    }

    public int getPlayerNum(){
        return playerNum;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public ArrayList<Boolean> getIsRoundDone() {
        return isRoundDone;
    }

    public void setIsRoundDone(ArrayList<Boolean> isRoundDone) {
        this.isRoundDone = isRoundDone;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}
    