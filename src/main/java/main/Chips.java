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
 * Description:
 *
 * ****************************************
 */
package main;

public class Chips {
    public double initAmount;
    public double currAmount;

    public Chips() {
        this.initAmount = 0;
        this.currAmount = 0;
    }

    public void subtractAmount(double value) {
        this.currAmount -= value;
    }

    public void addAmount(double value) {
        this.currAmount += value;
    }

}
    