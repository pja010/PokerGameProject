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


    public static final long serialVersionUID = 41L;

    private Deck deck;
    private Pot pot;
    private ArrayList<Card> tableCards;
    private ArrayList<Player> players;

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
        for(int i = 0; i < players.size();i++) {
            players.get(i).setCard2(deck.deal());
        }
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
    