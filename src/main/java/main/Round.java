/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Per Astrom
 * Section: 11:30
 * Date: 11/3/20
 * Time: 7:21 PM
 *
 * Project: csci205FinalProject
 * Package: main
 * Class: Round
 *
 * Description:
 *
 * ****************************************
 */
package main;

public class Round {

//    private int roundNum;
    private boolean isInPlay;

    public Round() {
//        this.roundNum = roundNum;
        this.isInPlay = false;
    }

//    public int getRoundNum() {
//        return roundNum;
//    }

    public void startRound() {
        this.isInPlay = true;
    }

    /**
     * Check status of round.
     * @return isInPlay is true if round is currently in play,
     * otherwise false.
     */
    public boolean isInPlay() {
        return isInPlay;
    }

    public void endRound() {
        this.isInPlay = false;
    }

    public static void main(String[] args) {
        Round round1 = new Round();
        System.out.println("Current game status: " + round1.isInPlay());
        System.out.println("Current game status: " + round1.isInPlay);
    }
}
