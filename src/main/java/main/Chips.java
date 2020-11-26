/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/2/20
 * Time: 12:53 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Chips
 *
 * Description: Poker chips object used by
 * players to make bets in a game of poker.
 *
 * ****************************************
 */
package main;

import java.util.Scanner;

/**
 * Class that encapsulates poker betting chips.
 */
public class Chips {

    /** Initial amount of chips player has. */
    public double initAmount;

    /** Current value of total amount of chips. */
    public double currAmount;

    /**
     * Variable to determine whether a player's betting amount is valid.
     */
    public boolean validAmount = false;

    /**
     * General constructor for amount of chips, setting initial and current amount to 0.
     */
    public Chips() {
        this.initAmount = 0;
        this.currAmount = 0;
    }

    /**
     * Subtracts amount player bets from their current amount.
     * @param betValue the amount a player bets.
     * Currently this checks the value a user inputs to make sure it is of correct value
     * but this functionality can easily be removed.
     */
    public void subtractAmount(double betValue) {
        Scanner scnr = new Scanner(System.in);
    while (!validAmount) {
        switch ((int) betValue) {
            // Player bets valid chip amount
            case 1:
            case 5:
            case 10:
            case 25:
            case 100:
                // Subtract bet amount from player
                this.currAmount -= betValue;
                validAmount = true;
                break;
            default:
                System.out.println("Please enter a valid poker chip amount to bet, (1), (5), (10), (25), or (100).");
                betValue = scnr.nextDouble();
        }
    }
    }

    /**
     * Adds more chips to the player's current amount.
     * @param value the amount of chips being added.
     */
    public void addAmount(double value) {
        this.currAmount += value;
    }

    @Override
    public String toString() {
        return "$" + currAmount;
    }
}

    