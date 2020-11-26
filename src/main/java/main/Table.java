/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/15/20
 * Time: 1:02 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Table
 *
 * Description:
 *
 * ****************************************
 */
package main;


import java.io.Serializable;

public class Table implements Serializable {


    public static final long serialVersionUID = 41L;

    private Deck deck;
    private Pot pot;


    public Table(){
//        deck = new Deck();
        pot = new Pot();
    }

    public Deck getDeck() {
        return deck;
    }

    public Pot getPot() {
        return pot;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setPot(Pot pot) {
        this.pot = pot;
    }

}
    