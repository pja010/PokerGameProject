/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/2/20
 * Time: 5:32 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: Score
 *
 * Description:
 *
 * ****************************************
 */
package main;

import java.util.ArrayList;

public class Score {

    private int score;

    public Score(ArrayList<Card> playerHand) {
        score = 0;
        for (int i = 0; i < playerHand.size(); i++){
            for (int j = i+1; j < playerHand.size(); j++) {
                for (int k = j+1; k < playerHand.size(); k++){
                    for (int p = k+1; p < playerHand.size(); p++) {
                        for (int q = p+1; q < playerHand.size(); q++) {
                            int tempScore = evaluate(playerHand.get(i), playerHand.get(j), playerHand.get(k), playerHand.get(p), playerHand.get(q));
                            if (tempScore > score) {
                                score = tempScore;
                            }
                        }
                    }
                }
            }
        }
    }

    public int evaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
        return 0;
    }

    public int getScore() {
        return score;
    }



}
    