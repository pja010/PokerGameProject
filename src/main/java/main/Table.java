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
import java.util.List;

/**
 * Poker table that encapsulates the deck, the community cards,
 * the pot and the players in the game to allow for a round of poker.
 */
public class Table implements Serializable {

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
    private String playerActionText1;
    private String playerActionText2;
    private String playerActionText3;
    private String playerActionText4;

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

    /**
     * Determines the winner of the round by calculating
     * the player with the highest scoring hand.
     *
     * @return the winning player.
     */
//    public ArrayList<Player> getWinner() {
//        ArrayList<Player> winner = players;
//
//        for (Player player : winner) {
//            if (player.isPlaying == false) {
//                winner.remove(player);
//            }
//        }
//
//        for (int i = 0; i < 5; i++) {
//            int maxScore = 0;
//            for (Player player : winner) {
//                if (player.getScore().getScore()[i] > maxScore) {
//                    maxScore = player.getScore().getScore()[i];
//                }
//            }
//            for (Player player : winner) {
//                if (player.getScore().getScore()[i] < maxScore) {
//                    winner.remove(player);
//                }
//            }
//        }
//        return winner;
//    }
    public String getPlayerActionText() {
        return playerActionText;
    }

    public void setPlayerActionText(String playerActionText) {
        this.playerActionText = playerActionText;
    }

    public void setPlayerActionText(String playerActionText, int playerNum) {
        if (playerNum == 1) {
            this.playerActionText1 = playerActionText;
        }
        else if (playerNum == 2) {
            this.playerActionText2 = playerActionText;
        }
        else if (playerNum == 3) {
            this.playerActionText3 = playerActionText;
        }

        else if (playerNum == 4) {
            this.playerActionText4 = playerActionText;
        }
    }


    public String getPlayerActionText1() {
        return playerActionText1;
    }

    public void setPlayerActionText1(String playerActionText1) {
        this.playerActionText1 = playerActionText1;
    }

    public String getPlayerActionText2() {
        return playerActionText2;
    }

    public void setPlayerActionText2(String playerActionText2) {
        this.playerActionText2 = playerActionText2;
    }

    public String getPlayerActionText3() {
        return playerActionText3;
    }

    public void setPlayerActionText3(String playerActionText3) {
        this.playerActionText3 = playerActionText3;
    }

    public String getPlayerActionText4() {
        return playerActionText4;
    }

    public void setPlayerActionText4(String playerActionText4) {
        this.playerActionText4 = playerActionText4;
    }


//    public static void main(String[] args) {
//        Table testTable = new Table();
//        Player player1 = new Player(1);
//        Player player2 = new Player(2);
//        testTable.addPlayer(player1);
//        testTable.addPlayer(player2);
//        player1.setPlayerAction(PlayerAction.CHECK);
//        player2.setPlayerAction(PlayerAction.BET);
//        player2.setBet(20);
//        testTable.playerActionDescription();
//    }

}
    