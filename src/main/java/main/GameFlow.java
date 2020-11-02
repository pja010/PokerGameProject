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


    public static void main(String[] args) throws EmptyDeckException {

        deck = new Deck();
        tableCards = new ArrayList<Card>();
        //deck.shuffle();

        initPlayers();

        initCard1(deck);

        initCard2(deck);

        initTableCards();

        System.out.println(player1.getPlayerHand());
        System.out.println(player1.getScore());
        System.out.println(player2.getPlayerHand());
        System.out.println(player1.getScore());
        System.out.println(player3.getPlayerHand());
        System.out.println(player1.getScore());
        System.out.println(player4.getPlayerHand());
        System.out.println(player1.getScore());

    }

    private static void initTableCards() throws EmptyDeckException {
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
        tableCards.add(deck.deal());
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

}
    