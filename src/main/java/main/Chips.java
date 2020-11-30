/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti, Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/2/20
 * Time: 12:53 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Chips
 *
 * Description: The betting chips used
 * in a poker game.
 *
 * ****************************************
 */
package main;

import java.io.Serializable;

/**
 * Class that encapsulates poker betting chips.
 */
 public class Chips implements Serializable {

    //private static final long serialVersionUID = 42L;

    /** Initial amount of chips player has */
    public double initAmount;

    /** Current value of total amount of chips. */
    public double currAmount;

    /**
     * General constructor for amount of chips, setting initial and current amount to 0.
     */
    public Chips() {
        this.initAmount = 0;
        this.currAmount = 0;
    }

    /**
     * Subtracts amount player bets from their current amount.
     * @param value amount player bets.
     */
    public void subtractAmount(double value) {
        this.currAmount -= value;
    }

    /**
     * Adds more chips to the player's current amount.
     * @param value the amount of chips being added.
     */
    public void addAmount(double value) {
        this.currAmount += value;
    }

    /**
     * @return  get the initial amount of chips.
     */
    public double getInitAmount() {
        return initAmount;
    }

    /**
     * @return get the current amount of chips.
     */
    public double getCurrAmount() {
        return currAmount;
    }

    /**
     * @return string representation of current amount of chips.
     */
    @Override
    public String toString() {
        return "$" + currAmount;
    }
}

    