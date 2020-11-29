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
import java.util.List;

public class Table implements Serializable {


    private Deck deck;
    private Pot pot;
    private ArrayList<Card> tableCards;
    private ArrayList<Player> players;
    private int round;
    private int turn;
    private int bet;
    private double betMin;

    public double getBetMin() {
        return betMin;
    }

    public void setBetMin(double betMin) {
        this.betMin = betMin;
    }


    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn){
        this.turn = turn;
    }



    public Table(){
        deck = new Deck();
        pot = new Pot();
        players = new ArrayList<Player>();
        tableCards = new ArrayList<Card>();
        turn = 1;
        this.betMin = 0;
        this.round = 1;
        this.bet = 0;
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
        deck.shuffle();
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

    public void changeTableCards() {
        deck.shuffle();
        deck.deal();
        tableCards.set(0,deck.deal());
        tableCards.set(1,deck.deal());
        tableCards.set(2,deck.deal());
        deck.deal();
        tableCards.set(3,deck.deal());
        deck.deal();
        tableCards.set(4,deck.deal());

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
        for(int i = 0; i < players.size();i++) {
            players.get(i).setCard2(deck.deal());
        }
    }

    public void reSetPlayerHands(){
        for (int i = 0; i < players.size(); i++){
            players.get(i).getPlayerHand().clear();
        }
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }


    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
    public String playerActionDescription() {
        String playerActionDescription = null;
        for (Player player : players) {
            if (player.getPlayerAction() == PlayerAction.BET)
                playerActionDescription = player.getUserName() + " raised by $" + player.getBet() + "";
            else if (player.getPlayerAction() == PlayerAction.CHECK)
                playerActionDescription = player.getUserName() + " checked";
            else if (player.getPlayerAction() == PlayerAction.FOLD)
                playerActionDescription = player.getUserName() + " folded";
        }
        return playerActionDescription;
    }

    public static void main(String[] args) {
        Table testTable = new Table();
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        testTable.addPlayer(player1);
        testTable.addPlayer(player2);
        player1.setPlayerAction(PlayerAction.CHECK);
        player2.setPlayerAction(PlayerAction.BET);
        player2.setBet(20);
        testTable.playerActionDescription();
    }

}
    