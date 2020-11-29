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
 * Package: main
 * Class: PlayerCopy
 *
 * Description: A player object for the poker game.
 *
 * ****************************************
 */
package main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class that models a player in a game of poker.
 */

public class PlayerCopy implements Serializable {

    /**
     * The player's game identification numbers.
     */
    private final int playerNum;
    public static final long serialVersionUID = 41L;

    /**
     * Whether or not the player is the dealer.
     */
    private boolean isDealer;

    /**
     * The player's hand represented as a list containing Card objects.
     */
    public ArrayList<Card> playerHand = new ArrayList<>();

    private Card card1;
    private Card card2;

    /**
     * The player's chips pile.
     */
    private Chips chips;

    /**
     * The score of the player's hand.
     */
    private ScoreUpdate score;

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

    /**
     * The amount of the player's bet.
     */
    private double bet;

    /**
     * Boolean to determine whether player is still active in round.
     */
    private ArrayList<Boolean> isRoundDone;

    /**
     * The player's user name.
     * It will be displayed to other players in the game.
     */
    private String userName;

    /**
     * Constructor initializes the player's fields.
     * @param playerNum the player's identification number.
     */
    public PlayerCopy(int playerNum) {
        this.playerNum = playerNum;
        this.chips = new Chips();
        this.isPlaying = true;
        this.playerAction = null;
        this.moveIsBetProperty = new SimpleBooleanProperty();
        this.moveIsCheckMoveProperty = new SimpleBooleanProperty();
        this.moveIsFoldProperty = new SimpleBooleanProperty();
        this.bet = 0;
        this.isRoundDone = new ArrayList<>();
    }

    /**
     * A second constructor that takes a simplified version of player
     * that can be passed in a networking thread, as an argument.
     * @param player the player object.
     */
    public PlayerCopy(Player player) {
        this.playerNum = player.getPlayerNum();
        this.chips = player.getChips();
        this.isPlaying = player.isPlaying;
        this.isDealer = player.isDealer();
        this.card1 = player.getPlayerHand().get(0);
        this.card2 = player.getPlayerHand().get(1);
        this.playerHand = player.getPlayerHand();
        this.chips = player.getChips();
        this.score = player.getScore();
        this.bet = player.getBet();
        this.isRoundDone = player.getIsRoundDone();


        this.playerAction = null;
        this.moveIsBetProperty = new SimpleBooleanProperty();
        this.moveIsCheckMoveProperty = new SimpleBooleanProperty();
        this.moveIsFoldProperty = new SimpleBooleanProperty();
    }

    /**
     * Makes a bet move.
     * @param betAmount the amount of chips to bet.
     */
    public void makeBetMove(double betAmount) {
        setPlayerAction(PlayerAction.BET);
        this.bet = betAmount;
        subtractChips(betAmount);
        System.out.println("Player" + playerNum + " made new bet of $" + betAmount);
    }

    /**
     * Makes a check move.
     */
    public void makeCheckMove() {
        setPlayerAction(PlayerAction.CHECK);
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
     * Gets the appropriate description of the player's betting action.
     * @return the description of the player's move.
     */
    public String playerActionDescription() {
        String playerActionDescription = null;
        if (this.getPlayerAction() == PlayerAction.BET)
            playerActionDescription = this.getUserName() + " raised by $" + this.getBet() + ".";
        else if (this.getPlayerAction() == PlayerAction.CHECK)
            playerActionDescription = this.getUserName() + " checked.";
        else if (this.getPlayerAction() == PlayerAction.FOLD)
            playerActionDescription = this.getUserName() + " folded.";
        return playerActionDescription;
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
    public ArrayList<Integer>  getScore() {
        score = new ScoreUpdate(playerHand);
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
        return String.valueOf(chips.currAmount);
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }
    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
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

    public ArrayList<Boolean> getIsRoundDone() {
        return isRoundDone;
    }

    public void setIsRoundDone(ArrayList<Boolean> isRoundDone) {
        this.isRoundDone = isRoundDone;
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

}
