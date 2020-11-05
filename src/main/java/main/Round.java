/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
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

/**
 * A class representing a round of poker,
 * consisting of up to 4 "betting-rounds".
 */
public class Round {
    /**
     * The number of the betting-round
     */
    private int roundNum;

    /**
     * The status of the round
     */
    private boolean isInPlay;

    /**
     * The players in the round
     */
    private static ArrayList<Player> playerList;

    /**
     * The cards on the table
     */
    private static ArrayList<Card> tableCards;

    /**
     * The deck of cards used in the round
     */
    private static Deck deck;

    /**
     * A player's betting amount
     */
    private static double bet;

    /**
     * The sum of the players' bets
     */
    private static Pot pot;

    /**
     * Constructor initializing the list of players,
     * the round status to false and the betting round to 1.
     * @param players is list of Player instances.
     */
    public Round(ArrayList players) {
        playerList = players;
        this.isInPlay = false;
        this.roundNum = 1;
    }

    /**
     * Start and execute a new round of poker.
     * @throws EmptyDeckException if card deck is empty.
     */
    public void startRound() throws EmptyDeckException {
        deck = new Deck();
        tableCards = new ArrayList<>();
        this.isInPlay = true;

        while(this.isInPlay) {
            switch (this.roundNum) {
                case (1):
                    pot = new Pot();
                    deck.shuffle();
                    initCard1(deck);
                    initCard2(deck);

                    //TODO - GET PLAYER ACTION (for all 4 cases)

                    getBets();

                    //SHOW PLAYERS TABLE CARDS 1-3
                    initTableCards();

                    this.roundNum += 1;

                case (2):

                    getBets();

                    this.roundNum += 1;
                case (3):
                    //SHOW PLAYERS TABLE CARD 4

                    //GET PLAYER ACTION

                    getBets();

                    this.roundNum += 1;

                case (4):
                    //SHOW PLAYERS TABLE CARD 5

                    //GET PLAYER ACTION

                    getBets();

                    // Give winner the pot
                    getWinner().addChips(pot.getTotalAmount());
                    System.out.println(getWinner().getPlayerNum() + " wins!");

                    // Clear players' hands
                    for (Player player : playerList) {
                        player.playerHand.clear();
                    }
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

    /**
     * End the current round.
     */
    public void endRound() {
        this.isInPlay = false;
    }

    /**
     * Get bets from all the players, for Player 1, we use information from the command line
     */
    private static void getBets() {
        for (Player player : playerList) {
            bet = 100;
            player.subChips(bet);
            pot.addToPot(bet);
        }
    }

    /**
     * Add 5 cards to the table from the deck.
     *
     * @throws EmptyDeckException if deck is empty.
     */
    private static void initTableCards() throws EmptyDeckException {
        deck.deal();
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        deck.deal();
        tableCards.add(deck.deal());
        deck.deal();
        tableCards.add(deck.deal());

        for (Player player : playerList) {
            addTableCardsToPlayer(player);
        }
    }

    // ToDo - could make initcards methods into one to avoid repetitive code
    /**
     * Deal the first card to each player.
     * @param deck contains the card to be dealt.
     * @throws EmptyDeckException - if deck is empty.
     */
    private static void initCard1(Deck deck) throws EmptyDeckException {
        for (Player player : playerList) {
            player.setCard2(deck.deal());
        }
    }

    /**
     * Deal the second card to each player.
     * @param deck contains the card to be dealt.
     * @throws EmptyDeckException - if deck is empty.
     */
    private static void initCard2(Deck deck) throws EmptyDeckException {
        for (Player player : playerList) {
            player.setCard2(deck.deal());
        }
    }

    /**
     * Add cards on table to players hand to get a total score.
     * @param player is instance of Player.
     */
    private static void addTableCardsToPlayer(Player player) {
        for (Card card : tableCards) {
            player.addCard(card);
        }

    }


    /**
     * Determine winner by finding the hand with the highest score.
     * @return winner is Player instance with winning hand.
     */
    public static Player getWinner(){
        Player winner = null;

        // Find max score among players
        int max = playerList.get(0).getScore();
        
        for (int i = 0; i < playerList.size(); ++i) {
            if (playerList.get(i).getScore() > max) {
                max = playerList.get(i).getScore();
            }
        }
        // Determine winner
        for (Player player : playerList) {
            if (player.getScore() == max) {
                winner = player;
            }
        }
        return winner;

    }

    /**
     * Get the number of the current betting round.
     * @return roundNum is an integer between 1-4.
     */
    public int getRoundNum() {
        return roundNum;
    }

    public static void main(String[] args) throws EmptyDeckException {
        Player p1 = new Player(1);
        Player p2 = new Player(2);
        Player p3 = new Player(3);
        Player p4 = new Player(4);

        ArrayList<Player> playerList = new ArrayList<>(4);
        playerList.add(p1);
        playerList.add(p2);
        playerList.add(p3);
//        playerList.add(p4);

//        Round testRound = new Round(p1,p2,p3,p4);
        Round testRound = new Round(playerList);
        testRound.startRound();



    }
}
