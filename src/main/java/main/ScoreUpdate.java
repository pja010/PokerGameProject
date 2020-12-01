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

    //private static final long serialVersionUID = 42L;

    private ArrayList<Integer> score;


    public ScoreUpdate(ArrayList<Card> playerHand) {
        this.score = new ArrayList<Integer>(5);
        score.add(0);
        score.add(0);
        score.add(0);
        score.add(0);
        score.add(0);

        for (int i = 0; i < playerHand.size(); i++){
            for (int j = i+1; j < playerHand.size(); j++) {
                for (int k = j+1; k < playerHand.size(); k++){
                    for (int p = k+1; p < playerHand.size(); p++) {
                        for (int q = p+1; q < playerHand.size(); q++) {
                            ArrayList<Integer> tempScore = new ArrayList<Integer>();
                            Card card1 = playerHand.get(i);
                            Card card2 = playerHand.get(j);
                            Card card3 = playerHand.get(k);
                            Card card4 = playerHand.get(p);
                            Card card5 = playerHand.get(q);
                            tempScore.addAll(evaluate(card1, card2, card3, card4, card5));
                            if (tempScore.get(0) >= this.score.get(0)) {
                                if (tempScore.get(0) > this.score.get(0)) {
                                    this.score = tempScore;
                                }
                                else if (tempScore.get(1) >= this.score.get(1)){
                                    if (tempScore.get(1) > this.score.get(1)) {
                                        this.score = tempScore;
                                    }
                                    else if (tempScore.get(2) >= this.score.get(2)){
                                        if (tempScore.get(2) > this.score.get(2)){
                                            this.score = tempScore;
                                        }
                                        else if(tempScore.get(3) >= this.score.get(3)){
                                            if (tempScore.get(3) > this.score.get(3)){
                                                this.score = tempScore;
                                            }
                                            else if(tempScore.get(4) >= this.score.get(4)){
                                                if (tempScore.get(4) > this.score.get(4)) {
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

    public ArrayList<Integer> evaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
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

        ArrayList<Integer> tempScore = new ArrayList<Integer>(5);
        tempScore.add(0);
        tempScore.add(0);
        tempScore.add(0);
        tempScore.add(0);
        tempScore.add(0);

        if (flush(ranks, suits) != 0 && straight(ranks) != 0){
            tempScore.set(0,flush(ranks, suits) + 300);
        }
        else if (kind(ranks) > 800){
            tempScore.set(0,kind(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(0)-800));
            ranks.remove(ranks.indexOf(tempScore.get(0)-800));
            ranks.remove(ranks.indexOf(tempScore.get(0)-800));
            ranks.remove(ranks.indexOf(tempScore.get(0)-800));
            tempScore.set(1,getMax(ranks));
        }
        else if (fullHouse(ranks) != 0) {
            tempScore.set(0,fullHouse(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(0)-700));
            ranks.remove(ranks.indexOf(tempScore.get(0)-700));
            ranks.remove(ranks.indexOf(tempScore.get(0)-700));
            tempScore.set(1,getMax(ranks));
        }
        else if (flush(ranks,suits) != 0){
            tempScore.set(0,flush(ranks,suits));
            ranks.remove(ranks.indexOf(tempScore.get(0)-600));
            tempScore.set(1,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(1)));
            tempScore.set(2,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(2)));
            tempScore.set(3,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(3)));
            tempScore.set(4,getMax(ranks));
        }
        else if (straight(ranks) != 0) {
            tempScore.set(0,straight(ranks));
        }
        else if (kind(ranks) > 400){
            tempScore.set(0,kind(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(0)-400));
            ranks.remove(ranks.indexOf(tempScore.get(0)-400));
            ranks.remove(ranks.indexOf(tempScore.get(0)-400));
            tempScore.set(1,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(1)));
            tempScore.set(2,getMax(ranks));
        }
        else if (twoPair(ranks) != 0){
            tempScore.set(0,twoPair(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(0)-300));
            ranks.remove(ranks.indexOf(tempScore.get(0)-300));
            tempScore.set(1,kind(ranks)-200);
            ranks.remove(ranks.indexOf(tempScore.get(1)));
            ranks.remove(ranks.indexOf(tempScore.get(1)));
            tempScore.set(2,getMax(ranks));
        }
        else if (kind(ranks) > 200){
            tempScore.set(0,kind(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(0)-200));
            ranks.remove(ranks.indexOf(tempScore.get(0)-200));
            tempScore.set(1,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(1)));
            tempScore.set(2,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(2)));
            tempScore.set(3,getMax(ranks));
        }
        else {
            tempScore.set(0,kind(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(0)-100));
            tempScore.set(1,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(1)));
            tempScore.set(2,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(2)));
            tempScore.set(3,getMax(ranks));
            ranks.remove(ranks.indexOf(tempScore.get(3)));
            tempScore.set(4,getMax(ranks));
        }


        return tempScore;
    }

    public ArrayList<Integer> getScore() {
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
    