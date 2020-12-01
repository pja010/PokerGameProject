/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/28/2020
 * Time: 1:00 PM
 *
 * Project: csci205FinalProject
 * Package: main
 * Class: ScoreUpdateTest
 *
 * Description: A test class for the Score
 * class
 *
 * ****************************************
 */
package main;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    /** Scored used in every test */
    private static Score score;

    /** Player used in every test */
    private static Player player1;

    /** 5 cards used in every test */
    private static Card card1;
    private static Card card2;
    private static Card card3;
    private static Card card4;
    private static Card card5;


    @BeforeEach
    void setUp(){
        // Initialize new player
        player1 = new Player(1);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * A test to evaluate a players hand of five cards
     */
    @Test
    void evaluate() {
        // Create a hand with the Ace of Diamonds, Ace of Clubs, Ace of Hearts, King of Diamonds, and King of Clubs
        ArrayList<Integer> ranks = new ArrayList(5);

        card1 = new Card(14,1);
        card2 = new Card(14,2);
        card3 = new Card(14,3);
        card4 = new Card(13,1);
        card5 = new Card(13,2);

        // This should be a full house, receive the same score as a full house in the first spot, and then
        // gets the rank of the two pair
        score = new Score(player1.getPlayerHand());

        ArrayList<Integer> values = new ArrayList<Integer>(Arrays.asList(714,13,0,0,0));
        assertEquals(values,score.evaluate(card1,card2,card3,card4,card5));

    }

    /**
     * A test to evaluate two of a kind, three of a kind, and four of a kind
     */
    @Test
    void kind() {
        ArrayList<Integer> ranks = new ArrayList(4);
        // Two of a kind - Create hand with Ace of Diamonds and Ace of Clubs
        card1 = new Card(14,1);
        card2 = new Card(14,2);
        // Add cards to player hand
        player1.addCard(card1);
        player1.addCard(card2);
        // Add card ranks
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());
        // Counts the two of a kind
        score = new Score(player1.getPlayerHand());
        assertEquals(214,score.kind(ranks));


        // Three of a kind - Add Ace of Hearts
        card3 = new Card(14,3);
        // Add card to player hand
        player1.addCard(card3);
        // Add card rank
        ranks.add(card3.getRank());
        // Counts the three of a kind
        score = new Score(player1.getPlayerHand());
        assertEquals(414,score.kind(ranks));


        // Four of a kind - Add Ace of Spades
        card4 = new Card(14,4);
        // Add card to player hand
        player1.addCard(card4);
        // Add card rank
        ranks.add(card4.getRank());
        // Counts the four of a kind
        score = new Score(player1.getPlayerHand());
        assertEquals(814,score.kind(ranks));
    }

    /***
     * A test to evaluate a full house
     */
    @Test
    void fullHouse() {
        // Create a hand with the Ace of Diamonds, Ace of Clubs, Ace of Hearts, King of Diamonds, and King of Clubs
        ArrayList<Integer> ranks = new ArrayList(5);
        card1 = new Card(14,1);
        card2 = new Card(14,2);
        card3 = new Card(14,3);
        card4 = new Card(13,1);
        card5 = new Card(13,2);

        // Add cards to player hand
        player1.addCard(card1);
        player1.addCard(card2);
        player1.addCard(card3);
        player1.addCard(card4);
        player1.addCard(card5);

        // Add card ranks
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());
        ranks.add(card3.getRank());
        ranks.add(card4.getRank());
        ranks.add(card5.getRank());

        // Counts the three pair and then evaluates the other two pair and adds 300
        score = new Score(player1.getPlayerHand());
        assertEquals(714,score.fullHouse(ranks));
    }

    /***
     * A test to evaluate a two pair
     */
    @Test
    void twoPair() {
        // Create a hand with the 2 of Clubs, 2 of Hearts, 6 of Spades, and 6 of Clubs
        ArrayList<Integer> ranks = new ArrayList(4);
        card1 = new Card(2,2);
        card2 = new Card(2 ,3);
        card3 = new Card(6,4);
        card4 = new Card(6,2);

        // Add cards to player hand
        player1.addCard(card1);
        player1.addCard(card2);
        player1.addCard(card3);
        player1.addCard(card4);

        // Add card ranks
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());
        ranks.add(card3.getRank());
        ranks.add(card4.getRank());

        // Counts the two pair from the highest pair and then adds 100
        score = new Score(player1.getPlayerHand());
        assertEquals(306,score.twoPair(ranks));
    }

    /***
     * A test to evaluate a flush
     */
    @Test
    void flush() {
        // Create a hand with the Ace of Diamonds, 2 of Diamonds, 5 of Diamonds, 7 of Diamonds, and King of Diamonds
        ArrayList<Integer> ranks = new ArrayList(5);
        ArrayList<Integer> suits = new ArrayList(5);

        card1 = new Card(14,1);
        card2 = new Card(2,1);
        card3 = new Card(5,1);
        card4 = new Card(7,1);
        card5 = new Card(13,1);

        // Add cards to player hand
        player1.addCard(card1);
        player1.addCard(card2);
        player1.addCard(card3);
        player1.addCard(card4);
        player1.addCard(card5);

        // Add card ranks
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());
        ranks.add(card3.getRank());
        ranks.add(card4.getRank());
        ranks.add(card5.getRank());

        // Add card suits
        suits.add(card1.getSuit());
        suits.add(card2.getSuit());
        suits.add(card3.getSuit());
        suits.add(card4.getSuit());
        suits.add(card5.getSuit());

        // Counts the highest ranked card and then adds 600
        score = new Score(player1.getPlayerHand());
        assertEquals(614,score.flush(ranks,suits));
    }

    /***
     * A test to evaluate a straight
     */
    @Test
    void straight() {
        // Create a hand with the 5 of Diamonds, 6 of Clubs, 7 of Hearts, 8 of Spades, and 9 of Clubs
        ArrayList<Integer> ranks = new ArrayList(5);

        card1 = new Card(5,1);
        card2 = new Card(6,2);
        card3 = new Card(7,3);
        card4 = new Card(8,4);
        card5 = new Card(9,2);

        // Add cards to player hand
        player1.addCard(card1);
        player1.addCard(card2);
        player1.addCard(card3);
        player1.addCard(card4);
        player1.addCard(card5);

        // Add card ranks
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());
        ranks.add(card3.getRank());
        ranks.add(card4.getRank());
        ranks.add(card5.getRank());

        // Counts the highest ranked card and then adds 500
        score = new Score(player1.getPlayerHand());
        assertEquals(509,score.straight(ranks));
    }

    /***
     * A test to get the highest rank from a set of cards
     */
    @Test
    void getMax() {
        // Create a hand with the Jack of Spades, Queen of Hearts, and 3 of Hearts.
        ArrayList<Integer> ranks = new ArrayList(3);

        card1 = new Card(11,4);
        card2 = new Card(12,3);
        card3 = new Card(3,4);

        // Add cards to player hand
        player1.addCard(card1);
        player1.addCard(card2);
        player1.addCard(card3);

        // Add card ranks
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());
        ranks.add(card3.getRank());

        // Counts whichever card has the highest rank
        score = new Score(player1.getPlayerHand());
        assertEquals(12,score.getMax(ranks));
    }

    /***
     * A test to evaluate the number of matches in a hand
     */
    @Test
    void countMatches() {
        // Create a hand with the 2 of Diamonds and 2 of Hearts
        ArrayList<Integer> ranks = new ArrayList(2);

        card1 = new Card(2,1);
        card2 = new Card(2,3);

        // Add cards to player hand
        player1.addCard(card1);
        player1.addCard(card2);

        // Add card ranks
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());

        // Counts number of matches based on rank
        score = new Score(player1.getPlayerHand());
        assertEquals(2,score.countMatches(ranks,2));
    }
}
