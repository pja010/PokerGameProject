/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Per Astrom
 * Section: 11:30
 * Date: 11/3/20
 * Time: 7:21 PM
 *
 * Project: csci205FinalProject
 * Package: main
 * Class: Round
 *
 * Description:
 *
 * ****************************************
 */
package main;

import java.util.ArrayList;

public class Round {

    private int roundNum;
    private boolean isInPlay;

    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;
    private static ArrayList<Card> tableCards;
    private static Deck deck;
    private static double bet1;
    private static double bet2;
    private static double bet3;
    private static double bet4;

    private static Pot pot;

    public Round(Player player1, Player player2, Player player3, Player player4) {
        this.isInPlay = false;
        this.roundNum = 1;

    }


    /**
     * Start and execute a new round of poker.
     * A round consists of up to four "betting rounds".
     *
     * @throws EmptyDeckException
     */
    public void startRound() throws EmptyDeckException {
        this.isInPlay = true;
        while(this.isInPlay) {
            switch (this.roundNum) {
                case (1):
                    pot = new Pot();
                    deck.shuffle();
                    initCard1(deck);
                    initCard2(deck);

                    //GET PLAYER ACTION

                    getBets();

                    // ADD ALL BETS TO POT
                    addBetsToPot();

                    //SHOW PLAYERS TABLE CARDS 1-3
                    initTableCards();

                    this.roundNum += 1;

                case (2):

                    //TODO - GET PLAYER ACTION
                    getBets();
                    addBetsToPot();

                    this.roundNum += 1;
                case (3):
                    //SHOW PLAYERS TABLE CARD 4

                    //GET PLAYER ACTION
                    getBets();
                    addBetsToPot();

                    this.roundNum += 1;

                case (4):
                    //SHOW PLAYERS TABLE CARD 5

                    //GET PLAYER ACTION

                    getBets();
                    addBetsToPot();

                    // gives winner the pot
                    getWinner().addChips(pot.getTotalAmount());

                    System.out.println(getWinner().getPlayerNum() + " wins!");

                    player1.playerHand.clear();
                    tableCards.clear();
                    endRound();
            }
        }
    }

    /**
     * Check status of round.
     * @return isInPlay - true if round is currently in play,
     * otherwise false.
     */
    public boolean isInPlay() {
        return isInPlay;
    }

    public void endRound() {
        this.isInPlay = false;
    }

    private static void addBetsToPot() {
        pot.addToPot(bet1);
        pot.addToPot(bet2);
        pot.addToPot(bet3);
        pot.addToPot(bet4);
    }

    /**
     * Get bets from all the players, for Player 1, we use information from the command line
     */
    private static void getBets() {
        bet1 = 100;
        player1.subChips(bet1);
        bet2 = 100;
        player2.subChips(bet2);
        bet3 = 100;
        player3.subChips(bet3);
        bet4 = 100;
        player4.subChips(bet4);

    }

    private static void initTableCards() throws EmptyDeckException {
        deck.deal();
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        deck.deal();
        tableCards.add(deck.deal());
        deck.deal();
        tableCards.add(deck.deal());

        addTableCardsToPlayer(player1);
        addTableCardsToPlayer(player2);
        addTableCardsToPlayer(player3);
        addTableCardsToPlayer(player4);
    }

    private static void initPlayers() {
        player1 = new Player(1);
        player2 = new Player(2);
        player3 = new Player(3);
        player4 = new Player(4);
    }

    private static void initCard2(Deck deck) throws EmptyDeckException {
        player1.setCard2(deck.deal());
        player2.setCard2(deck.deal());
        player3.setCard2(deck.deal());
        player4.setCard2(deck.deal());
    }

    private static void initCard1(Deck deck) throws EmptyDeckException {
        player1.setCard1(deck.deal());
        player2.setCard1(deck.deal());
        player3.setCard1(deck.deal());
        player4.setCard1(deck.deal());
    }

    private static void addTableCardsToPlayer(Player player) {
        for (int i = 0; i < tableCards.size(); i++){
            player.addCard(tableCards.get(i));
        }

    }

    private static void setChips() {
        player1.setChips(1600);
        player2.setChips(1600);
        player3.setChips(1600);
        player4.setChips(1600);
    }

    public static int getMax(int[] values){
        int max = values[0];
        for (int i = 1; i < values.length; i++){
            if (values[i] > max){
                max = values[i];
            }
        }
        return max;
    }

    public static Player getWinner(){
        int max = getMax(new int[]{player1.getScore(), player2.getScore(), player3.getScore(), player4.getScore()});
        if (player1.getScore() == max){
            return player1;
        }
        if (player2.getScore() == max){
            return player2;
        }
        if (player3.getScore() == max){
            return player3;
        }
        else {
            return player4;
        }
    }

    public int getRoundNum() {
        return roundNum;
    }





//
    public static void main(String[] args) throws EmptyDeckException {
//        Round round1 = new Round();
//        System.out.println("Current game status: " + round1.isInPlay());
//        System.out.println("Current game status: " + round1.isInPlay);

        deck = new Deck();
        tableCards = new ArrayList<Card>();

        deck.shuffle();

        initPlayers();
        setChips();

        Round round1 = new Round(player1, player2, player3, player4);
        System.out.println("Starting new round.");
        round1.startRound();

    // WE NEED TO ADD A BOOLEAN FOR EACH PLAYER TO CHECK IF THEY ARE PLAYING
    // TO CONTROL THIS

    // GETTING PLAYER ACTIONS AND BET AMOUNTS IS EVENTUALLY GOING
    // TO GO IN AN ORDER BUT FOR NOW LEAVING AS ONE PART

        System.out.println(player1.getChips());
        System.out.println(player2.getChips());
        System.out.println(player3.getChips());
        System.out.println(player4.getChips());

    }
}
