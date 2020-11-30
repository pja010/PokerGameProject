/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/2/20
 * Time: 12:03 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Card
 *
 * Description: Playing card object to be
 * used in a game of poker.
 *
 * ****************************************
 */
package main;

import java.io.Serializable;

/**
 * Class that encapsulates a playing card from a standard 52-deck of cards.
 * Encapsulates a card's rank and suit. The card is visually represented by a png image.
 * Based on ideas from: http://www.mathcs.emory.edu/~cheung/Courses/170/Syllabus/10/cards.html
 */
public class Card implements Serializable {

    //private static final long serialVersionUID = 42L;

    /**
     * The 4 possible suits and their relative values.
     */
    public static final int SPADE = 4;
    public static final int HEART = 3;
    public static final int CLUB = 2;
    public static final int DIAMOND = 1;

    /**
     * The 13 possible ranks and their values.
     */
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 14;

    /**
     * The rank and suit are encapsulated as integers.
     */
    private int rank;
    private int suit;


    /**
     * The general constructor for a playing card.
     * @param rank the rank of the card.
     * @param suit the suit of the card.
     */
    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suit=" + suit +
                '}';
    }

    public int getRank() {
        return rank;
    }

    public int getSuit() {
        return suit;
    }

}
    