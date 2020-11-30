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

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    //private static final long serialVersionUID = 42L;

    private int playerNum;
    private boolean isDealer;
    public ArrayList<Card> playerHand = new ArrayList<Card>();
    private Card card1;
    private Card card2;
    private String playerAction;
    private double actionAmount;
    private Chips chips;
    //private ScoreUpdate score;
    private double bet;
    public boolean isPlaying;
    public String userName;


    private ArrayList<Boolean> isRoundDone;

    public String getPlayerAction() {
        return playerAction;
    }

    public void setPlayerAction(String playerAction) {
        this.playerAction = playerAction;
    }


    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }


    public Player(int playerNum) {
        this.playerNum = playerNum;
        this.chips = new Chips();
        this.isPlaying = true;
        this.bet = 0;
        this.playerAction = null;
        this.isRoundDone = new ArrayList<Boolean>();
        this.isRoundDone.add(false);
        this.isRoundDone.add(false);
        this.isRoundDone.add(false);
        this.isRoundDone.add(false);

    }

    public void move(String action, double actionAmount) {
        this.playerAction = action;
        this.actionAmount = actionAmount;
        chips.subtractAmount(actionAmount);
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

    //public ScoreUpdate getScore() {
    //    score = new ScoreUpdate(playerHand);
    //    return score;
    //}

    public String playerActionDescription() {
        String playerActionDescription = null;
            if (this.getPlayerAction().equals("Bet"))
                playerActionDescription = this + " raised by $" + this.getBet() + ".";
            else if (this.getPlayerAction().equals("Check"))
                playerActionDescription = this + " checked.";
            else if (this.getPlayerAction().equals("Fold"))
                playerActionDescription = this + " folded.";
        return playerActionDescription;
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

    public static void main(String[] args) {
        Player player1 = new Player(1);
        player1.setPlayerAction("Check");
        System.out.print("Test: " + player1.playerActionDescription());
    }

    public ArrayList<Boolean> getIsRoundDone() {
        return isRoundDone;
    }

    public void setIsRoundDone(int i, Boolean isRoundDone) {
        this.isRoundDone.set(i,isRoundDone);
    }
}
    