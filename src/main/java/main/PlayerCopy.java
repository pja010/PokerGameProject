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
 *
 * ****************************************
 */
package main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class PlayerCopy implements Serializable {

    public static final long serialVersionUID = 41L;


    private int playerNum;
    private boolean isDealer;
    public ArrayList<Card> playerHand = new ArrayList<>();
    private Card card1;
    private Card card2;
    private Chips chips;
    private ScoreUpdate score;
    public boolean isPlaying;
    private PlayerAction playerAction;
    private SimpleBooleanProperty moveIsBetProperty;
    private SimpleBooleanProperty moveIsCheckMoveProperty;
    private SimpleBooleanProperty moveIsFoldProperty;
    private double bet;

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }






    /**
     * A factory to generate unique IDs in a safe way
     * From callie's lab06
     */
    private static class IDFactory {

        /** Collection of unique Employee IDs generated / assigned */
        private static HashSet<Integer> setOfAssignedIDs = new HashSet<>();

        /**
         * Method to assign a new ID number to an employee in the case that
         * the one provided in the constructor is less than zero or already taken
         *
         * @return id number that has not been used yet
         */
        private static Integer generateID() {
            Integer id = 1;
            for (int i = id; setOfAssignedIDs.contains(i); i++) {
                id = i + 1;
            }
            return id;
        }

        /**
         * Program to determine how to assign an ID to an employee
         *
         * @param idToCheck Integer ID
         * @return ID assigned to employee
         */
        private static Integer safeToUse(Integer idToCheck) {
            Integer empID;
            if (idToCheck <= 0 || setOfAssignedIDs.contains(idToCheck)){
                empID = IDFactory.generateID();
            }
            else {
                empID = idToCheck;
            }
            setOfAssignedIDs.add(empID);
            System.out.println("IDgen: " + empID);
            return empID;
        }
    }

    public PlayerCopy(int playerNum) {
        this.playerNum = IDFactory.safeToUse(playerNum);
        this.chips = new Chips();
        this.isPlaying = true;
        this.playerAction = null;
        this.moveIsBetProperty = new SimpleBooleanProperty();
        this.moveIsCheckMoveProperty = new SimpleBooleanProperty();
        this.moveIsFoldProperty = new SimpleBooleanProperty();
        this.bet = 0;

    }

    public PlayerCopy(Player player) {
        this.playerNum = IDFactory.safeToUse(player.getPlayerNum());
        this.chips = player.getChips();
        this.isPlaying = player.isPlaying;
        this.isDealer = player.isDealer();
        this.card1 = player.getPlayerHand().get(0);
        this.card2 = player.getPlayerHand().get(1);
        this.playerHand = player.getPlayerHand();
        this.chips = player.getChips();
        this.score = player.getScore();
        this.bet = player.getBet();


        this.playerAction = null;
        this.moveIsBetProperty = new SimpleBooleanProperty();
        this.moveIsCheckMoveProperty = new SimpleBooleanProperty();
        this.moveIsFoldProperty = new SimpleBooleanProperty();

    }

    public void makeBetMove(double betAmount) {
        setPlayerAction(PlayerAction.BET);
        this.bet = betAmount;
//        subtractChips(betAmount);
        chips.subtractAmount(betAmount);
        System.out.println("Player" + playerNum + " made new bet of $" + betAmount);
    }

    public void makeCheckMove() {
        setPlayerAction(PlayerAction.CHECK);
        // ToDo - get current bet amount from previous player and subtract
//        subtractChips();
        System.out.println("Player" + playerNum + " checked.");
    }

    public void makeFoldMove() {
        setPlayerAction(PlayerAction.FOLD);
        this.isPlaying = false;
        System.out.println("Player" + playerNum + " folded.");
    }

    public void setChips(double initAmount) {
        this.chips.initAmount = initAmount;
        this.chips.currAmount = initAmount;
    }

    public void setCard1(Card card1){
        this.card1 = card1;
        this.playerHand.add(card1);
    }

    public void setCard2(Card card2){
        this.card2 = card2;
        this.playerHand.add(card2);
    }

    public void addCard(Card card) {
        this.playerHand.add(card);
    }

    public int[] getScore() {
        score = new ScoreUpdate(playerHand);
        return score.getScore();
    }

    public void addChips(double amount){
        chips.currAmount += amount;
    }

    public void subtractChips(double amount){
        chips.currAmount -= amount;
    }

    public double getChips(){
        return chips.currAmount;
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
}
