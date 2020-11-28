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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ScoreUpdate implements Serializable {

    public static final long serialVersionUID = 41L;

    private int[] score;


    public ScoreUpdate(ArrayList<Card> playerHand) {
        this.score = new int[5];
        for (int i = 0; i < playerHand.size(); i++){
            for (int j = i+1; j < playerHand.size(); j++) {
                for (int k = j+1; k < playerHand.size(); k++){
                    for (int p = k+1; p < playerHand.size(); p++) {
                        for (int q = p+1; q < playerHand.size(); q++) {
                            int[] tempScore = evaluate(playerHand.get(i), playerHand.get(j), playerHand.get(k), playerHand.get(p), playerHand.get(q));
                            if (tempScore[0] >= this.score[0]) {
                                if (tempScore[0] > this.score[0]) {
                                    this.score = tempScore;
                                }
                                else if (tempScore[1] >= this.score[1]){
                                    if (tempScore[1] > this.score[1]) {
                                        this.score = tempScore;
                                    }
                                    else if (tempScore[2] >= this.score[2]){
                                        if (tempScore[2] > this.score[2]){
                                            this.score = tempScore;
                                        }
                                        else if(tempScore[3] >= this.score[3]){
                                            if (tempScore[3] > this.score[3]){
                                                this.score = tempScore;
                                            }
                                            else if(tempScore[4] >= this.score[4]){
                                                if (tempScore[4] > this.score[4]) {
                                                    this.score = tempScore;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    public int[] evaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
        ArrayList<Integer> ranks = new ArrayList<Integer>();
        ranks.add(card1.getRank());
        ranks.add(card2.getRank());
        ranks.add(card3.getRank());
        ranks.add(card4.getRank());
        ranks.add(card5.getRank());
        ArrayList<Integer> suits = new ArrayList<Integer>();
        suits.add(card1.getSuit());
        suits.add(card2.getSuit());
        suits.add(card3.getSuit());
        suits.add(card4.getSuit());
        suits.add(card5.getSuit());

        int[] tempScore = new int[5];

        if (flush(ranks, suits) != 0 && straight(ranks) != 0){
            tempScore[0] = flush(ranks, suits) + 300;
        }
        else if (kind(ranks) > 800){
            tempScore[0] = kind(ranks);
            ranks.remove(ranks.indexOf(tempScore[0]-800));
            ranks.remove(ranks.indexOf(tempScore[0]-800));
            ranks.remove(ranks.indexOf(tempScore[0]-800));
            ranks.remove(ranks.indexOf(tempScore[0]-800));
            tempScore[1] = getMax(ranks);
        }
        else if (fullHouse(ranks) != 0) {
            tempScore[0] = fullHouse(ranks);
            ranks.remove(ranks.indexOf(tempScore[0]-700));
            ranks.remove(ranks.indexOf(tempScore[0]-700));
            ranks.remove(ranks.indexOf(tempScore[0]-700));
            tempScore[1] = getMax(ranks);
        }
        else if (flush(ranks,suits) != 0){
            tempScore[0] = flush(ranks,suits);
            ranks.remove(ranks.indexOf(tempScore[0]-600));
            tempScore[1] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[1]));
            tempScore[2] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[2]));
            tempScore[3] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[3]));
            tempScore[4] = getMax(ranks);
        }
        else if (straight(ranks) != 0) {
            tempScore[0] = straight(ranks);
        }
        else if (kind(ranks) > 400){
            tempScore[0] = kind(ranks);
            ranks.remove(ranks.indexOf(tempScore[0]-400));
            ranks.remove(ranks.indexOf(tempScore[0]-400));
            ranks.remove(ranks.indexOf(tempScore[0]-400));
            tempScore[1] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[1]));
            tempScore[2] = getMax(ranks);
        }
        else if (twoPair(ranks) != 0){
            tempScore[0] = twoPair(ranks);
            ranks.remove(ranks.indexOf(tempScore[0]-300));
            ranks.remove(ranks.indexOf(tempScore[0]-300));
            tempScore[1] = kind(ranks)-200;
            ranks.remove(ranks.indexOf(tempScore[1]));
            ranks.remove(ranks.indexOf(tempScore[1]));
            tempScore[2] = getMax(ranks);
        }
        else if (kind(ranks) > 200){
            tempScore[0] = kind(ranks);
            ranks.remove(ranks.indexOf(tempScore[0]-200));
            ranks.remove(ranks.indexOf(tempScore[0]-200));
            tempScore[1] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[1]));
            tempScore[2] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[2]));
            tempScore[3] = getMax(ranks);
        }
        else {
            tempScore[0] = kind(ranks);
            ranks.remove(ranks.indexOf(tempScore[0]-100));
            tempScore[1] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[1]));
            tempScore[2] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[2]));
            tempScore[3] = getMax(ranks);
            ranks.remove(ranks.indexOf(tempScore[3]));
            tempScore[4] = getMax(ranks);
        }


        return tempScore;
    }

    public int[] getScore() {
        return score;
    }

    public int kind(ArrayList<Integer> ranks){
        int count = 0;
        int rank = 0;
        for(int i = 0; i < ranks.size(); i++){
            if (countMatches(ranks, ranks.get(i)) > count){
                count = countMatches(ranks, ranks.get(i));
                rank = ranks.get(i);
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
    public int fullHouse(ArrayList<Integer> ranks){
        int count = 0;
        ArrayList<Integer> ranks1 = new ArrayList<>();
        for (int i = 0; i < ranks.size(); i++){
            ranks1.add(ranks.get(i));
        }
        if (500 > kind(ranks) && kind(ranks) > 400){
            ranks1.remove(ranks1.indexOf(kind(ranks) - 400));
            ranks1.remove(ranks1.indexOf(kind(ranks) - 400));
            ranks1.remove(ranks1.indexOf(kind(ranks) - 400));
            if (kind(ranks1) > 200){
                count = kind(ranks) + 300;
            }
        }
        return count;
    }

    public int twoPair(ArrayList<Integer> ranks){
        int count = 0;
        ArrayList<Integer> ranks1 = new ArrayList<>();
        for (int i = 0; i < ranks.size(); i++){
            ranks1.add(ranks.get(i));
        }
        if (300 > kind(ranks) && kind(ranks) > 200){
            ranks1.remove(ranks1.indexOf(kind(ranks) - 200));
            ranks1.remove(ranks1.indexOf(kind(ranks) - 200));

            if (kind(ranks1) > 200){
                ArrayList<Integer> numbers = new ArrayList<Integer>();
                numbers.add(kind(ranks));
                numbers.add(kind(ranks1));
                count = getMax(numbers) + 100;
            }
        }
        return count;
    }

    public int flush(ArrayList<Integer> ranks, ArrayList<Integer> suits) {
        for (int i = 1; i < suits.size(); i++){
            if (suits.get(0) != suits.get(i)){
                return 0;
            }
        }
        return 600 + getMax(ranks);
    }
    public int straight(ArrayList<Integer> ranks){
        Collections.sort(ranks);
        for (int i = 0; i < ranks.size()-1; i++) {
            if (ranks.get(i) + 1 != ranks.get(i+1)){
                return 0;
            }
        }
        return 500 + getMax(ranks);
    }

    public int getMax(ArrayList<Integer> values){
        int max = values.get(0);
        for (int i = 1; i < values.size(); i++){
            if (values.get(i) > max){
                max = values.get(i);
            }
        }
        return max;
    }

    public int countMatches(ArrayList<Integer> ranks, int rank){
        int count = 0;
        for (int i = 0; i < ranks.size(); i++){
            if (ranks.get(i) == rank){
                count += 1;
            }
        }
        return count;
    }



}
    