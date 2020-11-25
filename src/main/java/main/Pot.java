/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/3/2020
 * Time: 1:00 PM
 *
 * Project: csci205FinalProject
 * Package: main
 * Class: Pot
 *
 * Description: Represents the total amount of money
 * that has been bet and records minimum bet necessary
 *
 * ****************************************
 */
package main;

import java.io.Serializable;

public class Pot implements Serializable {


    /** Total amount of money in the pot */
    private double totalAmount;

    /**
     * General constructor for pot, initially, pot is empty
     */
    public Pot(){
        this.totalAmount = 0;
    }

    /**
     * Add bet amount to pot
     * @param amount of chips that were bet
     */
    public void addToPot(double amount) {
        this.totalAmount += amount;
    }

    /**
     * Checks if pot is empty
     * @return true if there is no money in the pot
     */
    public boolean isEmpty(){
        return this.totalAmount == 0;
    }

    /**
     * @return total amount in Pot
     */
    public double getTotalAmount() {
        return this.totalAmount;
    }

    /**
     * @return string representation of total amount in Pot
     */
    @Override
    public String toString() {
        return "Total Pot Amount is " + this.totalAmount;
    }
}
