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
import java.util.Arrays;

public class Score {

    private int score;

    public Score(ArrayList<Card> playerHand) {
        this.score = 0;
        for (int i = 0; i < playerHand.size(); i++){
            for (int j = i+1; j < playerHand.size(); j++) {
                for (int k = j+1; k < playerHand.size(); k++){
                    for (int p = k+1; p < playerHand.size(); p++) {
                        for (int q = p+1; q < playerHand.size(); q++) {
                            int tempScore = evaluate(playerHand.get(i), playerHand.get(j), playerHand.get(k), playerHand.get(p), playerHand.get(q));
                            if (tempScore > this.score) {
                                this.score = tempScore;
                            }
                        }
                    }
                }
            }
        }
    }

    public int evaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
        int[] ranks = {card1.getRank(), card2.getRank(), card3.getRank(), card4.getRank(), card5.getRank()};
        int[] suits = {card1.getSuit(), card2.getSuit(), card3.getSuit(), card4.getSuit(), card5.getSuit()};

        int tempScore = 0;

        if (flush(ranks, suits) != 0 && straight(ranks) != 0){
            tempScore = flush(ranks, suits) + 300;
        }
        else if (kind(ranks) > 800){
            tempScore = kind(ranks);
        }
        else if (fullHouse(ranks) != 0) {
            tempScore = fullHouse(ranks);
        }
        else if (flush(ranks,suits) != 0){
            tempScore = flush(ranks,suits);
        }
        else if (straight(ranks) != 0) {
            tempScore = straight(ranks);
        }
        else if (kind(ranks) > 400){
            tempScore = kind(ranks);
        }
        else if (twoPair(ranks) != 0){
            tempScore = twoPair(ranks);
        }
        else {
            tempScore = kind(ranks);
        }


        return tempScore;
    }

    public int getScore() {
        return score;
    }

    public int kind(int[] ranks){
        int count = 0;
        int rank = 0;
        for(int i = 0; i < ranks.length; i++){
            if (countMatches(ranks, ranks[i]) > count){
                count = countMatches(ranks, ranks[i]);
                rank = ranks[i];
            }
        }
        if (count == 4){
            return 800 + rank;
        }
        else if (count == 3){
            return 400 + rank;
        }
        else if (count == 2){
            return 200 + rank;
        }
        else {
            return 100+getMax(ranks);
        }
    }
    public int fullHouse(int[] ranks){
        int count = 0;
        ArrayList<Integer> ranks1 = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++){
            ranks1.add(ranks[i]);
        }
        if (500 > kind(ranks) && kind(ranks) > 400){
            int[] ranks2 = new int[2];
            ranks1.remove(ranks1.indexOf(kind(ranks) - 400));
            ranks1.remove(ranks1.indexOf(kind(ranks) - 400));
            ranks1.remove(ranks1.indexOf(kind(ranks) - 400));
            for (int i = 0; i < ranks1.size(); i++){
                ranks2[i] = ranks1.get(i);
            }
            if (kind(ranks2) > 200){
                count = kind(ranks) + 300;
            }
        }
        return count;
    }

    public int twoPair(int[] ranks){
        int count = 0;
        ArrayList<Integer> ranks1 = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++){
            ranks1.add(ranks[i]);
        }
        if (300 > kind(ranks) && kind(ranks) > 200){
            int[] ranks2 = new int[3];
            ranks1.remove(ranks1.indexOf(kind(ranks) - 200));
            ranks1.remove(ranks1.indexOf(kind(ranks) - 200));
            for (int i = 0; i < ranks1.size(); i++){
                ranks2[i] = ranks1.get(i);
            }
            if (kind(ranks2) > 200){
                count = getMax(new int[]{kind(ranks), kind(ranks2)}) + 100;
            }
        }
        return count;
    }

    public int flush(int[] ranks, int[] suits) {
        for (int i = 1; i < suits.length; i++){
            if (suits[0] != suits[i]){
                return 0;
            }
        }
        return 600 + getMax(ranks);
    }
    public int straight(int[] ranks){
        Arrays.sort(ranks);
        for (int i = 0; i < ranks.length-1; i++) {
            if (ranks[i] + 1 != ranks[i+1]){
                return 0;
            }
        }
        return 500 + getMax(ranks);
    }

    public int getMax(int[] values){
        int max = values[0];
        for (int i = 1; i < values.length; i++){
            if (values[i] > max){
                max = values[i];
            }
        }
        return max;
    }

    public int countMatches(int[] ranks, int rank){
        int count = 0;
        for (int i = 0; i < ranks.length; i++){
            if (ranks[i] == rank){
                count += 1;
            }
        }
        return count;
    }



}
    