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

import java.util.Scanner;

public class Pot {
    /** Total amount of money in the pot */
    public double totalAmount;

    /** Keeps track of minimum betting value */
    public double minBettingValue = 5.0;

    /** Bet previously made */
    public double previousBet;


//    /** Keeps track of current betting round */
//    public BetRound betRound;

    /**
     * General constructor for pot, initially, pot is empty
     */
    public Pot(){
        this.totalAmount = 0;
        this.previousBet = 0;
    }

    /**
     * Add bet amount to pot
     * @param amount of chips that were bet
     */
    public void addToPot(double amount) {
        totalAmount += amount;
        previousBet = amount;
        System.out.printf("%.2f dollars have been added to the pot. \n", amount);
//        System.out.printf("Next player must bet at least $%.2f. \n", amount);
    }

    /**
     * Checks if pot is empty
     * @return true if there is no money in the pot
     */
    public boolean isEmpty(){
        return totalAmount == 0;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPreviousBet() {
        return previousBet;
    }

    @Override
    public String toString() {
        return "Total Pot Amount is " + totalAmount +
                ", Minimum betting value is " + minBettingValue +
                ", and the previous bet was " + previousBet;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Player player1 = new Player(1);
        System.out.println("Please enter initial amount of chips");
        int initialAmount = scnr.nextInt();
        player1.setChips(initialAmount);
        System.out.println("Player " + player1.getPlayerNum() + " has " + player1.getChips());
        Chips chips = player1.getChips();
        System.out.println("Please enter amount to bet");
        double amountToBet = scnr.nextDouble();
        chips.subtractAmount(amountToBet);
        System.out.println("Player " + player1.getPlayerNum() + " has " + player1.getChips());
        System.out.println(chips.getPot());
    }
}
