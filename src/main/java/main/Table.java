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
 * Description:Poker table that contains
 * objects to be used in a round of poker.
 *
 * ****************************************
 */
package main;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Poker table that encapsulates the deck, the community cards,
 * the pot and the players in the game to allow for a round of poker.
 */
public class Table implements Serializable {

    //private static final long serialVersionUID = 42L;

    /**
     * Deck of cards.
     */
    private Deck deck;

    /**
     * The chips pot consists of all players' bets.
     */
    private Pot pot;

    /**
     * The community cards.
     */
    private ArrayList<Card> tableCards;

    /**
     * The players in the round.
     */
    private ArrayList<Player> players;

    /**
     * The round.
     */
    private int round;
    private int turn;
    private int bet;
    private double betMin;

    /**
     * The text to display player's betting actions.
     */

    private String playerActionText;
    private ArrayList<String> playerActionTexts;

    /**
     * The minimum allowed bet.
     */
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

    public String getPlayerTurnMessage() {
        return playerTurnMessage;
    }

    public void setPlayerTurnMessage(String playerTurnMessage) {
        this.playerTurnMessage = playerTurnMessage;
    }

    private String playerTurnMessage;

    /**
     * Constructor initializes the fields.
     * The minimum allowed betting amount is set to 0.
     */
    public Table(){
        deck = new Deck();
        pot = new Pot();
        players = new ArrayList<Player>();
        tableCards = new ArrayList<Card>();
        turn = 1;
        this.betMin = 0;
        this.round = 1;
        this.bet = 0;
        this.playerActionText = null;
        playerActionTexts = new ArrayList<String>();
        playerActionTexts.add("");
        playerActionTexts.add("");
        playerActionTexts.add("");
        playerActionTexts.add("");
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

    /**
     * Sets the Flop, Turn and River Cards on the table,
     * and adds these to the player's hands.
     */
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

    /**
     * Adds new player to the game.
     * @param player the player to be added.
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    /**
     * Deals two cards to each player in the game.
     */
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


    public String getPlayerActionText() {
        return playerActionText;
    }

    public void setPlayerActionText(String playerActionText) {
        this.playerActionText = playerActionText;
    }

    public void setPlayerActionText() {
        this.playerActionTexts.set(0,players.get(0).playerActionDescription());

        this.playerActionTexts.set(1,players.get(1).playerActionDescription());

        this.playerActionTexts.set(2,players.get(2).playerActionDescription());

        this.playerActionTexts.set(3,players.get(3).playerActionDescription());


    }



    public ArrayList<String> getPlayerActionTexts() {
        return playerActionTexts;
    }

    public void setPlayerActionTexts(ArrayList<String> playerActionTexts) {
        this.playerActionTexts = playerActionTexts;
    }
}
    