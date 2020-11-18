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
 * Description:
 *
 * ****************************************
 */
package main;

import javafx.scene.image.Image;

import java.io.Serializable;

/**
 * http://www.mathcs.emory.edu/~cheung/Courses/170/Syllabus/10/cards.html
 */
public class Card implements Serializable {

    public static final int SPADE = 4;
    public static final int HEART = 3;
    public static final int CLUB = 2;
    public static final int DIAMOND = 1;

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

    private int rank;
    private int suit;

    private Image image;

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
        String filename = rank + "_" + suit + ".png";
        image = new Image(this.getClass().getResource("/DeckOfCards/" + filename).toString());
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
    