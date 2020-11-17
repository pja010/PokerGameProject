/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/2/20
 * Time: 1:04 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Deck
 *
 * Description:
 *
 * ****************************************
 */
package main;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

class EmptyDeckException extends Exception {
    public EmptyDeckException(String message) {
        super(message);
    }
}

/**
 * http://www.mathcs.emory.edu/~cheung/Courses/170/Syllabus/10/deck-of-cards.html
 */
public class Deck {

    private ArrayList<Card> deckOfCards;
    private int cardsDealt;
    private Image backOfCard;

    public Deck(ArrayList<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
        backOfCard = new Image("./main/resources/DeckOfCards/back_of_card.png");
    }

    public Deck() {
        deckOfCards = new ArrayList<Card>();
        for (int suit = Card.DIAMOND; suit <= Card.SPADE; suit ++) {
            for (int rank = Card.TWO; rank <= Card.ACE; rank ++) {
                deckOfCards.add(new Card(rank,suit));
            }
        }
        cardsDealt = 0;
        backOfCard = new Image("./main/resources/DeckOfCards/back_of_card.png");
    }

    /**
     * Based off of
     * https://introcs.cs.princeton.edu/java/15inout/Shuffle.java.html
     */
    public void shuffle() {
        int j = 0;
        while(j < 10) {
            for (int i = 0; i < deckOfCards.size(); i++) {
                Random random = new Random();
                int index = random.nextInt(deckOfCards.size());
                Card card = deckOfCards.get(i);
                deckOfCards.remove(i);
                deckOfCards.add(index, card);
            }
            j += 1;
        }
        cardsDealt = 0;
    }

    public Card deal() throws EmptyDeckException{
        if (cardsDealt <= 52) {
            Card card = deckOfCards.get(0);
            deckOfCards.remove(0);
            deckOfCards.add(card);
            cardsDealt += 1;
            return card;
        }
        else {
            throw new EmptyDeckException("Empty Deck!");
        }
    }

    public ArrayList<Card> getDeckOfCards() {
        return deckOfCards;
    }

    public void setDeckOfCards(ArrayList<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    public Image getBackOfCard() {
        return backOfCard;
    }

    public void setBackOfCard(Image backOfCard) {
        this.backOfCard = backOfCard;
    }


}