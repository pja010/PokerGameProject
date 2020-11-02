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

import java.util.ArrayList;

enum PlayerAction {
    FOLD,
    CHECK,
    BET,
    RAISE
}

public class Player {
    private int playerNum;
    private boolean isDealer;
    private ArrayList<Card> playerHand = new ArrayList<Card>();
    private Card card1;
    private Card card2;
    private PlayerAction action;
    private double actionAmount;
    private Chips chips;
    private Score score;

    public Player(int playerNum) {
        this.playerNum = playerNum;

    }

    public void move(PlayerAction action, double actionAmount) {
        this.action = action;
        this.actionAmount = actionAmount;
        chips.subtractAmount(actionAmount);
    }

    public void setChips(int initAmount) {
        this.chips.initAmount = initAmount;
        this.chips.currAmount = this.chips.initAmount;
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

    public int getScore() {
        score = new Score(playerHand);
        return score.getScore();
    }


    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }
}
    