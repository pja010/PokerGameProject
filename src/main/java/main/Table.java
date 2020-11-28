/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/15/20
 * Time: 1:02 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Table
 *
 * Description:
 *
 * ****************************************
 */
package main;


import java.io.Serializable;
import java.util.ArrayList;

public class Table implements Serializable {


    private Deck deck;
    private Pot pot;
    private ArrayList<Card> tableCards;
    private ArrayList<Player> players;
    private int round;

    public double getBetMin() {
        return betMin;
    }

    public void setBetMin(double betMin) {
        this.betMin = betMin;
    }

    private double betMin;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn){
        this.turn = turn;
    }

    private int turn;


    public Table(){
        deck = new Deck();
        pot = new Pot();
        players = new ArrayList<Player>();
        tableCards = new ArrayList<Card>();
        turn = 1;
        this.betMin = 0;
        this.round = 1;
    }

    public Deck getDeck() {
        return deck;
    }

    public Pot getPot() {
        return pot;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setTableCards() {
        deck.deal();
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        deck.deal();
        tableCards.add(deck.deal());
        deck.deal();
        tableCards.add(deck.deal());

        for(int i = 0; i < players.size(); i++){
            for(int j = 0; j < tableCards.size(); j++){
                players.get(i).addCard(tableCards.get(j));
            }
        }
    }

    public ArrayList<Card> getTableCards(){
        return tableCards;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void setPlayerCards(){
        for(int i = 0; i < players.size();i++){
            players.get(i).setCard1(deck.deal());
        }
        for(int i = 0; i < players.size();i++){
            players.get(i).setCard2(deck.deal());
        }
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ArrayList<Player> getWinner(){
        ArrayList<Player> winner = players;

        for(Player player : winner){
            if (player.isPlaying == false){
                winner.remove(player);
            }
        }

        for(int i = 0; i < 5;i++) {
            int maxScore = 0;
            for (Player player : winner) {
                if (player.getScore().getScore()[i] > maxScore) {
                    maxScore = player.getScore().getScore()[i];
                }
            }
            for (Player player : winner) {
                if (player.getScore().getScore()[i] < maxScore) {
                    winner.remove(player);
                }
            }
        }
        return winner;
    }
}
    