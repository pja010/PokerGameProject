/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/10/20
 * Time: 6:27 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: TestScore
 *
 * Description:
 *
 * ****************************************
 */
package main;

import java.util.ArrayList;

public class TestScore {

    private static Deck deck;
    private static ArrayList<Card> tableCards;
    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;

    public static void main(String[] args) throws EmptyDeckException {
        int[] probs = new int[9];
        int i = 0;
        while (i < 10000){
            i += 1;
            deck = new Deck();
            deck.shuffle();
            tableCards = new ArrayList<Card>();

            initPlayers();

            initCards(deck);


            initTableCards();

            //System.out.println(player1.getPlayerHand());
            //System.out.print(player1.getScore()[0] + ", ");
            //System.out.print(player1.getScore()[1] + ", ");
            //System.out.print(player1.getScore()[2] + ", ");
            //System.out.print(player1.getScore()[3] + ", ");
            //System.out.println(player1.getScore()[4]);
            //System.out.println(player2.getPlayerHand());
            //System.out.print(player2.getScore()[0] + ", ");
            //System.out.print(player2.getScore()[1] + ", ");
            //System.out.print(player2.getScore()[2] + ", ");
            //System.out.print(player2.getScore()[3] + ", ");
            //System.out.println(player2.getScore()[4]);
            //System.out.println(player3.getPlayerHand());
            //System.out.print(player3.getScore()[0] + ", ");
            //System.out.print(player3.getScore()[1] + ", ");
            //System.out.print(player3.getScore()[2] + ", ");
            //System.out.print(player3.getScore()[3] + ", ");
            //System.out.println(player3.getScore()[4]);
            //System.out.println(player4.getPlayerHand());
            //System.out.print(player4.getScore()[0] + ", ");
            //System.out.print(player4.getScore()[1] + ", ");
            //System.out.print(player4.getScore()[2] + ", ");
            //System.out.print(player4.getScore()[3] + ", ");
            //System.out.println(player4.getScore()[4]);


            int[] score = {player1.getScore().getScore()[0],player2.getScore().getScore()[0], player3.getScore().getScore()[0], player4.getScore().getScore()[0]};

            for(int j = 0; j < score.length; j++){
                if (score[j] > 900){
                    probs[8] += 1;
                }
                else if (score[j] > 800){
                    probs[7] += 1;
                }
                else if (score[j] > 700){
                    probs[6] += 1;
                }
                else if (score[j] > 600){
                    probs[5] += 1;
                }
                else if (score[j] > 500){
                    probs[4] += 1;
                }
                else if (score[j] > 400){
                    probs[3] += 1;
                }
                else if (score[j] > 300){
                    probs[2] += 1;
                }
                else if (score[j] > 200){
                    probs[1] += 1;
                }
                else if (score[j] > 100){
                    probs[0] += 1;
                }
            }

        }
        for (int k = 0; k < probs.length; k++){
            System.out.print(probs[k]*100/40000.00 + ", ");
        }


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

        for (Card card:tableCards){
            player1.addCard(card);
            player2.addCard(card);
            player3.addCard(card);
            player4.addCard(card);
        }
    }

    private static void initCards(Deck deck) throws EmptyDeckException {
        player1.setCard1(deck.deal());
        player2.setCard1(deck.deal());
        player3.setCard1(deck.deal());
        player4.setCard1(deck.deal());
        player1.setCard2(deck.deal());
        player2.setCard2(deck.deal());
        player3.setCard2(deck.deal());
        player4.setCard2(deck.deal());
    }

    private static void initPlayers() {
        player1 = new Player(1);
        player2 = new Player(2);
        player3 = new Player(3);
        player4 = new Player(4);
    }
}
    