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
 * Description: Class that evaluates a poker hand
 * and determines the score.
 *
 * ****************************************
 */
package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class that determines the total score of player's hand in a game of poker.
 */
public class ScoreUpdate implements Serializable {

    /**
     * List containing the 5 cards constituting a player's hand.
     */
    private ArrayList<Integer> score;

    /**
     * Size of a poker player hand.
     */
    private static final int PLAYER_HAND_SIZE = 5;

    /**
     * Updates the score of the hand.
     * @param playerHand a list of a player's 5 cards.
     *///todo - shorten this method
    public ScoreUpdate(ArrayList<Card> playerHand) {
        this.score = new ArrayList<>(PLAYER_HAND_SIZE);
        setInitialHand(score);

        for (int i = 0; i < playerHand.size(); i++){
            for (int j = i+1; j < playerHand.size(); j++) {
                for (int k = j+1; k < playerHand.size(); k++){
                    for (int p = k+1; p < playerHand.size(); p++) {
                        for (int q = p+1; q < playerHand.size(); q++) {
                            Card card1 = playerHand.get(i);
                            Card card2 = playerHand.get(j);
                            Card card3 = playerHand.get(k);
                            Card card4 = playerHand.get(p);
                            Card card5 = playerHand.get(q);
                            ArrayList<Integer> tempScore = new ArrayList<>(evaluate(card1, card2, card3, card4, card5));
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

    /**
     * Initialize the score of the cards in a player's hands to a default value of 0.
     * @param playerHandScores the scores of the cards in a hand.
     */
    private void setInitialHand(ArrayList<Integer> playerHandScores) {
        for (int i = 0; i < PLAYER_HAND_SIZE; i++) {
            playerHandScores.add(0);
        }
    }

//todo shorten this method / split it into submethods, and also decrease number of parameters if possible
    /**
     * Evaluate the score of a player's hand.
     * @param card1
     * @param card2
     * @param card3
     * @param card4
     * @param card5
     * @return
     */
    public ArrayList<Integer> evaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
        ArrayList<Integer> ranks = new ArrayList<>();
        ranks.add(card1.getRank()); //todo avoid duplicated code
        ranks.add(card2.getRank());
        ranks.add(card3.getRank());
        ranks.add(card4.getRank());
        ranks.add(card5.getRank());

        ArrayList<Integer> suits = new ArrayList<>();
        suits.add(card1.getSuit());
        suits.add(card2.getSuit());
        suits.add(card3.getSuit());
        suits.add(card4.getSuit());
        suits.add(card5.getSuit());

        ArrayList<Integer> tempScore = new ArrayList<>(5);
        setInitialHand(tempScore);

        if (flush(ranks, suits) != 0 && straight(ranks) != 0){
            tempScore.set(0,flush(ranks, suits) + 300);
        }
        else if (kind(ranks) > 800){ //todo avoid magic numbers
            tempScore.set(0,kind(ranks));
            ranks.remove(tempScore.get(0) - 800);
            ranks.remove(tempScore.get(0) - 800);
            ranks.remove(tempScore.get(0) - 800);
            ranks.remove(tempScore.get(0) - 800);
            tempScore.set(1,getMax(ranks));
        }
        else if (fullHouse(ranks) != 0) {
            tempScore.set(0,fullHouse(ranks));
            ranks.remove(tempScore.get(0) - 700);
            ranks.remove(tempScore.get(0) - 700);
            ranks.remove(tempScore.get(0) - 700);
            tempScore.set(1,getMax(ranks));
        }
        else if (flush(ranks,suits) != 0){
            tempScore.set(0,flush(ranks,suits));
            ranks.remove(tempScore.get(0) - 600);
            tempScore.set(1,getMax(ranks));
            ranks.remove(tempScore.get(1));
            tempScore.set(2,getMax(ranks));
            ranks.remove(tempScore.get(2));
            tempScore.set(3,getMax(ranks));
            ranks.remove(tempScore.get(3));
            tempScore.set(4,getMax(ranks));
        }
        else if (straight(ranks) != 0) {
            tempScore.set(0,straight(ranks));
        }
        else if (kind(ranks) > 400){
            tempScore.set(0,kind(ranks));
            ranks.remove(tempScore.get(0) - 400);
            ranks.remove(tempScore.get(0) - 400);
            ranks.remove(tempScore.get(0) - 400);
            tempScore.set(1,getMax(ranks));
            ranks.remove(tempScore.get(1));
            tempScore.set(2,getMax(ranks));
        }
        else if (twoPair(ranks) != 0){
            tempScore.set(0,twoPair(ranks));
            ranks.remove(tempScore.get(0) - 300);
            ranks.remove(tempScore.get(0) - 300);
            tempScore.set(1,kind(ranks)-200);
            ranks.remove(tempScore.get(1));
            ranks.remove(tempScore.get(1));
            tempScore.set(2,getMax(ranks));
        }
        else if (kind(ranks) > 200){
            tempScore.set(0,kind(ranks));
            ranks.remove(tempScore.get(0) - 200);
            ranks.remove(tempScore.get(0) - 200);
            tempScore.set(1,getMax(ranks));
            ranks.remove(tempScore.get(1));
            tempScore.set(2,getMax(ranks));
            ranks.remove(tempScore.get(2));
            tempScore.set(3,getMax(ranks));
        }
        else {
            tempScore.set(0,kind(ranks));
            ranks.remove(tempScore.get(0) - 100);
            tempScore.set(1,getMax(ranks));
            ranks.remove(tempScore.get(1));
            tempScore.set(2,getMax(ranks));
            ranks.remove(tempScore.get(2));
            tempScore.set(3,getMax(ranks));
            ranks.remove(tempScore.get(3));
            tempScore.set(4,getMax(ranks));
        }


        return tempScore;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    /**
     * Evaluates the number of matching score of an "n"-of-a-kind hand.
     * @param ranks the ranks of the cards.
     * @return the score of the hand.
     */
    public int kind(ArrayList<Integer> ranks){
        int kindCount = 0;
        int rank = 0;
        for(int i = 0; i < ranks.size(); i++){
            if (countMatches(ranks, ranks.get(i)) > kindCount){
                kindCount = countMatches(ranks, ranks.get(i));
                rank = ranks.get(i);
            }
        }
        if (kindCount == 4){
            return 800 + rank;
        }
        else if (kindCount == 3){
            return 400 + rank;
        }
        else if (kindCount == 2){
            return 200 + rank;
        }
        else {
            return 100+getMax(ranks);
        }
    }

    /**
     * Evaluates the count of a full house hand.
     * @param ranks the ranks of the cards.
     * @return the count.
     */
    public int fullHouse(ArrayList<Integer> ranks){
        int count = 0;
        ArrayList<Integer> ranks1 = new ArrayList<>(ranks);
        if (500 > kind(ranks) && kind(ranks) > 400){
            ranks1.remove(kind(ranks) - 400);
            ranks1.remove(kind(ranks) - 400);
            ranks1.remove(kind(ranks) - 400);
            if (kind(ranks1) > 200){
                count = kind(ranks) + 300;
            }
        }
        return count;
    }

    /**
     * Evaluates the count of a two pair hand.
     * @param ranks the ranks of the cards.
     * @return the count.
     */
    public int twoPair(ArrayList<Integer> ranks){
        int count = 0;
        ArrayList<Integer> ranks1 = new ArrayList<>(ranks);
        if (300 > kind(ranks) && kind(ranks) > 200){
            ranks1.remove(kind(ranks) - 200);
            ranks1.remove(kind(ranks) - 200);

            if (kind(ranks1) > 200){
                ArrayList<Integer> numbers = new ArrayList<>();
                numbers.add(kind(ranks));
                numbers.add(kind(ranks1));
                count = getMax(numbers) + 100;
            }
        }
        return count;
    }

    /**
     * Evaluates the count of a flush.
     * @param ranks the ranks of the cards.
     * @param suits the suits of the cards.
     * @return the count.
     */
    public int flush(ArrayList<Integer> ranks, ArrayList<Integer> suits) {
        for (int i = 1; i < suits.size(); i++){
            if (!suits.get(0).equals(suits.get(i))){
                return 0;
            }
        }
        return 600 + getMax(ranks);
    }

    /**
     * Evaluates the count of a straight.
     * @param ranks the ranks of the cards.
     * @return the count.
     */
    public int straight(ArrayList<Integer> ranks){
        Collections.sort(ranks);
        for (int i = 0; i < ranks.size()-1; i++) {
            if (ranks.get(i) + 1 != ranks.get(i+1)){
                return 0;
            }
        }
        return 500 + getMax(ranks);
    }

    /**
     * Gets the highest value in a list of integers.
     * @param values the integer list.
     * @return the highest value.
     */
    public int getMax(ArrayList<Integer> values){
        int max = values.get(0);
        for (int i = 1; i < values.size(); i++){
            if (values.get(i) > max){
                max = values.get(i);
            }
        }
        return max;
    }

    /**
     * Counts the number of cards of the same rank in a hand.
     * @param ranks the ranks of the cards.
     * @param rank the rank to be compare for matches.
     * @return the count.
     */
    public int countMatches(ArrayList<Integer> ranks, int rank){
        int count = 0;
        for (Integer integer : ranks) {
            if (integer == rank) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Gets the winner in the game; the player with the highest scoring hand.
     * @param players the poker players in a list.
     * @return the winning player.
     */
    public static ArrayList<Player> getWinner(ArrayList<Player> players){
        ArrayList<Player> winner = new ArrayList<>(players);

        winner.removeIf(player -> !player.isPlaying);

        for(int i = 0; i < 5;i++) {
            int maxScore = 0;
            for (Player player : winner) {
                if (player.getScore().getScore().get(i) > maxScore) {
                    maxScore = player.getScore().getScore().get(i);
                }
            }
            for (Player player : winner) {
                if (player.getScore().getScore().get(i) < maxScore) {
                    winner.remove(player);
                }
            }
        }
        return winner;
    }



}
    