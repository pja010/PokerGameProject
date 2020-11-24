/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 * Name: Per Astrom
 * Section: 11:30
 * Date: 11/2/20
 * Time: 11:59 AM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Player
 *
 * Description: A player object for the poker game.
 *
 * ****************************************
 */
package main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;

/**
 * Class that models a player in a game of poker.
 */

public class PlayerCopy {

    /**
     * The player's game identification number.
     */
    private final int playerNum;
    
    /**
     * Whether or not the player is the dealer.
     */
    private boolean isDealer;

    /**
     * The player's hand represented as a list containing Card objects. 
     */
    public ArrayList<Card> playerHand = new ArrayList<>();

    /**
     * The player's chips pile.
     */
    private Chips chips;

    /**
     * The status of the player; true if player is in the game, otherwise false.
     */
    public boolean isPlaying;

    /**
     * The player's choice of move.
     */
    private PlayerAction playerAction;

    /**
     * Properties for the player's various moves.
     */
    private SimpleBooleanProperty moveIsBetProperty;
    private SimpleBooleanProperty moveIsCheckMoveProperty;
    private SimpleBooleanProperty moveIsFoldProperty;
    private SimpleDoubleProperty chipsProperty;

    /**
     * Overloaded constructor that initializes the player's properties.
     * @param playerNum the player's game identification number.    
     */
    public PlayerCopy(int playerNum) {
        this.playerNum = playerNum;
        this.chips = new Chips();
        this.isPlaying = true;
        this.playerAction = null;
        this.moveIsBetProperty = new SimpleBooleanProperty();
        this.moveIsCheckMoveProperty = new SimpleBooleanProperty();
        this.moveIsFoldProperty = new SimpleBooleanProperty();
        this.chipsProperty = new SimpleDoubleProperty();

    }

    /**
     * Makes a bet move.
     * @param betAmount the amount of chips to bet.
     */
    public void makeBetMove(double betAmount) {
        setPlayerAction(PlayerAction.BET);
//        chips.subtractAmount(betAmount);
        subtractChips(betAmount);
        System.out.println("Player" + playerNum + " made new bet of $" + betAmount);
    }

    /**
     * Makes a check move.
     */
    public void makeCheckMove() {
        setPlayerAction(PlayerAction.CHECK);
        // ToDo - get current bet amount from previous player and subtract
//        subtractChips();
        System.out.println("Player" + playerNum + " checked.");
    }

    /**
     * Makes a fold move.
     */
    public void makeFoldMove() {
        setPlayerAction(PlayerAction.FOLD);
        this.isPlaying = false;
        System.out.println("Player" + playerNum + " folded.");
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
        this.playerHand.add(card1);
    }

    /**
     * Sets the second card in the player's hand.
     * @param card2 the type of card to be set.
     */
    public void setCard2(Card card2){
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
    public int[] getScore() {
        /**
         * The score of the player's hand.
         */
        ScoreUpdate score = new ScoreUpdate(playerHand);
        return score.getScore();
    }

    /**
     * Adds chips to the player's pile.
     * @param amount the amount of chips to be added.
     */
    public void addChips(double amount){
        chips.currAmount += amount;
    }

    /**
     * Subtracts chips from the player's pile.
     * @param amount the amount of chips to be subtracted.
     */
    public void subtractChips(double amount){
        chips.currAmount -= amount;
    }

    public double getChips(){
        return chips.currAmount;
    }

    public String getChipsAsString() {
        String sChips = String.valueOf(chips.currAmount);
        return sChips;
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


    public Boolean getMoveIsBetProperty() {
        return moveIsBetProperty.get();
    }

    public BooleanProperty moveIsBetPropertyProperty() {
        return moveIsBetProperty;
    }

    public PlayerAction getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(PlayerAction playerAction) {
        this.playerAction = playerAction;
    }

    public boolean isMoveIsCheckMoveProperty() {
        return moveIsCheckMoveProperty.get();
    }

    public SimpleBooleanProperty moveIsCheckMovePropertyProperty() {
        return moveIsCheckMoveProperty;
    }

    public boolean isMoveIsFoldProperty() {
        return moveIsFoldProperty.get();
    }

    public SimpleBooleanProperty moveIsFoldPropertyProperty() {
        return moveIsFoldProperty;
    }

    public void setMoveIsFoldProperty(boolean moveIsFoldProperty) {
        this.moveIsFoldProperty.set(moveIsFoldProperty);
    }

    public double getChipsProperty() {
        return chipsProperty.get();
    }

    public SimpleDoubleProperty chipsPropertyProperty() {
        return chipsProperty;
    }

    public void setChipsProperty(double chipsProperty) {
        this.chipsProperty.set(chipsProperty);
    }
}
