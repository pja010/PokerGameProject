/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/2/20
 * Time: 5:49 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: GameFlow
 *
 * Description:
 *
 * ****************************************
 */
package main;

import java.util.ArrayList;

public class GameFlow {

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



    public static void main(String[] args) throws EmptyDeckException {

        deck = new Deck();
        tableCards = new ArrayList<Card>();

        deck.shuffle();

        initPlayers();
        setChips();

        int round = 0;

        boolean isPlaying = true;
        // WE NEED TO ADD A BOOLEAN FOR EACH PLAYER TO CHECK IF THEY ARE PLAYING
        // TO CONTROL THIS

        // GETTING PLAYER ACTIONS AND BET AMOUNTS IS EVENTUALLY GOING
        // TO GO IN AN ORDER BUT FOR NOW LEAVING AS ONE PART

        while(isPlaying){
            round += 1;
            double POT = 0;

            deck.shuffle();

            initCard1(deck);

            initCard2(deck);

            initTableCards();

            //GET PLAYER ACTION

            // GET BETTING AMOUNTS &&
            // REDUCE PLAYER CHIPS BY THEIR BET AMOUNT
            getBets();

            // ADD ALL BETS TO POT
            POT += bet1 + bet2 + bet3 + bet4;

            //SHOW PLAYERS TABLE CARDS 1-3

            //GET PLAYER ACTION

            // GET BETTING AMOUNTS &&
            // REDUCE PLAYER CHIPS BY THEIR BET AMOUNT
            getBets();

            // ADD ALL BETS TO POT
            POT += bet1 + bet2 + bet3 + bet4;

            //SHOW PLAYERS TABLE CARD 4

            //GET PLAYER ACTION

            // GET BETTING AMOUNTS &&
            // REDUCE PLAYER CHIPS BY THEIR BET AMOUNT
            getBets();

            // ADD ALL BETS TO POT
            POT += bet1 + bet2 + bet3 + bet4;


            //SHOW PLAYERS TABLE CARD 5

            //GET PLAYER ACTION

            // GET BETTING AMOUNTS &&
            // REDUCE PLAYER CHIPS BY THEIR BET AMOUNT
            getBets();

            // ADD ALL BETS TO POT
            POT += bet1 + bet2 + bet3 + bet4;

            // gives winner the pot
            getWinner().addChips(POT);

            System.out.println(getWinner().getPlayerNum() + " wins!");

            player1.playerHand.clear();
            tableCards.clear();

            if (round >= 4){
                isPlaying = false;
            }
        }

        System.out.println(player1.getChips());
        System.out.println(player2.getChips());
        System.out.println(player3.getChips());
        System.out.println(player4.getChips());
    }

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


}
    