/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/29/2020
 * Time: 11:45 AM
 *
 * Project: csci205FinalProject
 * Package: main
 * Class: DeckTest
 *
 * Description: A test class for the Deck class
 *
 * ****************************************
 */
package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    /** Deck used in every test */
    private static Deck deck;


    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    /***
     * A test class to ensure the deck is getting shuffled
     */
    @Test
    void shuffle() {
        ArrayList<Card> oldDeckOfCards = new ArrayList<>(52);
        // Copy the old deck of cards into array list
        for (int i = 0; i < 51; i++) {
            oldDeckOfCards.add(deck.getDeckOfCards().get(i));
        }

        // Shuffle the deck
        deck.shuffle();

        // Check if the first 2 cards were shuffled
        for (int cardNum = 0; cardNum < 2; cardNum++) {
            // Get a card of the old deck
            Card card = oldDeckOfCards.get(cardNum);

            // Get the new first card of the deck
            Card newCard = deck.getDeckOfCards().get(cardNum);

            assertNotEquals(card,newCard);
        }
    }

    /***
     * A test to ensure the first card of the deck is getting dealt
     */
    @Test
    void deal() {
        // Get the first card of the deck
        Card card = deck.getDeckOfCards().get(0);
        assertEquals(card,deck.deal());
    }
}