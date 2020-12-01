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
import java.util.Arrays;
import java.util.Collections;

/**
 * Class that determines the total score of player's hand in a game of poker.
 */
public class ScoreUpdate implements Serializable {

    //private static final long serialVersionUID = 42L;

    /**
     * Scores for hands.
     */
    private final int SCORE_800 = 800;
    private final int SCORE_700 = 700;
    private final int SCORE_600 = 600;
    private final int SCORE_400 = 400;
    private final int SCORE_300 = 300;
    private final int SCORE_200 = 200;
    private final int SCORE_100 = 100;

    /**
     * Card hand positions.
     */
    private final int FIRST_CARD_POS = 0;
    private final int SECOND_CARD_POSITION = 1;
    private final int THIRD_CARD_POSITION = 2;
    private final int FOURTH_CARD_POSITION = 3;
    private final int FIFTH_CARD_POSITION = 4;
    private final int SCORE_500 = 500;

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
     */
    public ScoreUpdate(ArrayList<Card> playerHand) {
        this.score = new ArrayList<>(PLAYER_HAND_SIZE);
        setInitialHand(score);

        for (int i = FIRST_CARD_POS; i < playerHand.size(); i++){
            for (int j = i+SECOND_CARD_POSITION; j < playerHand.size(); j++) {
                for (int k = j+SECOND_CARD_POSITION; k < playerHand.size(); k++){
                    for (int p = k+SECOND_CARD_POSITION; p < playerHand.size(); p++) {
                        for (int q = p+SECOND_CARD_POSITION; q < playerHand.size(); q++) {
                            Card card1 = playerHand.get(i);
                            Card card2 = playerHand.get(j);
                            Card card3 = playerHand.get(k);
                            Card card4 = playerHand.get(p);
                            Card card5 = playerHand.get(q);

                            ArrayList<Integer> tempScore = new ArrayList<>(evaluate(card1, card2, card3, card4, card5));
                            updatePlayerScoreFromTempScore(tempScore);
                        }
                    }
                }
            }
        }
    }

    private void updatePlayerScoreFromTempScore(ArrayList<Integer> tempScore) {
        int firstCardScore = this.score.get(FIRST_CARD_POS);
        int secondCardScore = this.score.get(SECOND_CARD_POSITION);
        int thirdCardScore = this.score.get(THIRD_CARD_POSITION);
        int fourthCardScore = this.score.get(FOURTH_CARD_POSITION);
        int fifthCardScore = this.score.get(FIFTH_CARD_POSITION);

        if (tempScore.get(FIRST_CARD_POS) >= firstCardScore) {
            if (tempScore.get(FIRST_CARD_POS) > firstCardScore) {
                this.score = tempScore;
            }
            else if (tempScore.get(SECOND_CARD_POSITION) >= secondCardScore) {
                if (tempScore.get(SECOND_CARD_POSITION) > secondCardScore) {
                    this.score = tempScore;
                }
                else if (tempScore.get(THIRD_CARD_POSITION) >= thirdCardScore) {
                    if (tempScore.get(THIRD_CARD_POSITION) > thirdCardScore) {
                        this.score = tempScore;
                    }
                    else if(tempScore.get(FOURTH_CARD_POSITION) >= fourthCardScore) {
                        if (tempScore.get(FOURTH_CARD_POSITION) > fourthCardScore) {
                            this.score = tempScore;
                        }
                        else if(tempScore.get(FIFTH_CARD_POSITION) >= fifthCardScore) {
                            if (tempScore.get(FIFTH_CARD_POSITION) > fifthCardScore) {
                                this.score = tempScore;
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
        for (int i = FIRST_CARD_POS; i < PLAYER_HAND_SIZE; i++) {
            addCardRankToRanks(playerHandScores, FIRST_CARD_POS);
        }
    }

    /**
     * Evaluate the score of a player's hand.
     * @param card1 the first card on hand.
     * @param card2 the second card on hand.
     * @param card3 the third card on hand.
     * @param card4 the fourth card on hand.
     * @param card5 the fifth card on hand.
     * @return the hand score.
     */
    public ArrayList<Integer> evaluate(Card card1, Card card2, Card card3, Card card4, Card card5){
        ArrayList<Integer> ranks = new ArrayList<>();
        ArrayList<Integer> suits = new ArrayList<>();
        ArrayList<Card> cardsOnHand = new ArrayList<>(PLAYER_HAND_SIZE);

        cardsOnHand.add(card1);
        cardsOnHand.add(card2);
        cardsOnHand.add(card3);
        cardsOnHand.add(card4);
        cardsOnHand.add(card5);

        for (Card card : cardsOnHand) {
            addCardRankToRanks(ranks, card.getRank());
            addCardSuitToSuits(ranks, card.getSuit());
        }

        ArrayList<Integer> tempScore = new ArrayList<>(PLAYER_HAND_SIZE);
        setInitialHand(tempScore);

        if (flush(ranks, suits) != 0 && straight(ranks) != 0){
            tempScore.set(FIRST_CARD_POS,flush(ranks, suits) + SCORE_300);
        }
        else {
            if (kind(ranks) > SCORE_800){
                tempScore.set(FIRST_CARD_POS,kind(ranks));
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_800);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_800);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_800);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_800);
                tempScore.set(SECOND_CARD_POSITION,getMax(ranks));
            }
            else if (fullHouse(ranks) != 0) {
                tempScore.set(FIRST_CARD_POS,fullHouse(ranks));
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_700);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_700);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_700);
                tempScore.set(SECOND_CARD_POSITION,getMax(ranks));
            }
            else if (flush(ranks,suits) != 0){
                tempScore.set(FIRST_CARD_POS,flush(ranks,suits));
                setTempScore(ranks, tempScore, SCORE_600);
            }
            else if (straight(ranks) != FIRST_CARD_POS) {
                tempScore.set(FIRST_CARD_POS,straight(ranks));
            }
            else if (kind(ranks) > SCORE_400){
                tempScore.set(FIRST_CARD_POS,kind(ranks));
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_400);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_400);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_400);
                tempScore.set(SECOND_CARD_POSITION,getMax(ranks));
                ranks.remove(tempScore.get(SECOND_CARD_POSITION));
                tempScore.set(THIRD_CARD_POSITION,getMax(ranks));
            }
            else if (twoPair(ranks) != FIRST_CARD_POS){
                tempScore.set(FIRST_CARD_POS,twoPair(ranks));
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_300);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_300);
                tempScore.set(SECOND_CARD_POSITION,kind(ranks)- SCORE_200);
                ranks.remove(tempScore.get(SECOND_CARD_POSITION));
                ranks.remove(tempScore.get(SECOND_CARD_POSITION));
                tempScore.set(THIRD_CARD_POSITION,getMax(ranks));
            }
            else if (kind(ranks) > SCORE_200){
                tempScore.set(FIRST_CARD_POS,kind(ranks));
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_200);
                ranks.remove(tempScore.get(FIRST_CARD_POS) - SCORE_200);
                tempScore.set(SECOND_CARD_POSITION,getMax(ranks));
                ranks.remove(tempScore.get(SECOND_CARD_POSITION));
                tempScore.set(THIRD_CARD_POSITION,getMax(ranks));
                ranks.remove(tempScore.get(THIRD_CARD_POSITION));
                tempScore.set(FOURTH_CARD_POSITION,getMax(ranks));
            }
            else {
                tempScore.set(FIRST_CARD_POS,kind(ranks));
                setTempScore(ranks, tempScore, SCORE_100);
            }
        }


        return tempScore;
    }

    private void setTempScore(ArrayList<Integer> ranks, ArrayList<Integer> tempScore, int score_600) {
        ranks.remove(tempScore.get(FIRST_CARD_POS) - score_600);
        tempScore.set(SECOND_CARD_POSITION,getMax(ranks));
        ranks.remove(tempScore.get(SECOND_CARD_POSITION));
        tempScore.set(THIRD_CARD_POSITION,getMax(ranks));
        ranks.remove(tempScore.get(THIRD_CARD_POSITION));
        tempScore.set(FOURTH_CARD_POSITION,getMax(ranks));
        ranks.remove(tempScore.get(FOURTH_CARD_POSITION));
        tempScore.set(FIFTH_CARD_POSITION,getMax(ranks));
    }

    private void addCardSuitToSuits(ArrayList<Integer> suits, int suit) {
        suits.add(suit);
    }

    private void addCardRankToRanks(ArrayList<Integer> ranks, int cardRank) {
        ranks.add(cardRank);
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
        for(int i = FIRST_CARD_POS; i < ranks.size(); i++){
            if (countMatches(ranks, ranks.get(i)) > kindCount){
                kindCount = countMatches(ranks, ranks.get(i));
                rank = ranks.get(i);
            }
        }
        if (kindCount == 4){
            return SCORE_800 + rank;
        }
        else if (kindCount == 3){
            return SCORE_400 + rank;
        }
        else if (kindCount == 2){
            return SCORE_200 + rank;
        }
        else {
            return SCORE_100 + getMax(ranks);
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
        if (SCORE_500 > kind(ranks) && kind(ranks) > SCORE_400){
            ranks1.remove(kind(ranks) - SCORE_400);
            ranks1.remove(kind(ranks) - SCORE_400);
            ranks1.remove(kind(ranks) - SCORE_400);
            if (kind(ranks1) > SCORE_200){
                count = kind(ranks) + SCORE_300;
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
        if (SCORE_300 > kind(ranks) && kind(ranks) > SCORE_200){
            ranks1.remove(kind(ranks) - SCORE_200);
            ranks1.remove(kind(ranks) - SCORE_200);

            if (kind(ranks1) > SCORE_200){
                ArrayList<Integer> numbers = new ArrayList<>();
                numbers.add(kind(ranks));
                numbers.add(kind(ranks1));
                count = getMax(numbers) + SCORE_100;
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
        for (int i = SECOND_CARD_POSITION; i < suits.size(); i++){
            if (!suits.get(FIRST_CARD_POS).equals(suits.get(i))){
                return 0;
            }
        }
        return SCORE_600 + getMax(ranks);
    }

    /**
     * Evaluates the count of a straight.
     * @param ranks the ranks of the cards.
     * @return the count.
     */
    public int straight(ArrayList<Integer> ranks){
        Collections.sort(ranks);
        for (int i = FIRST_CARD_POS; i < ranks.size() - 1; i++) {
            if (ranks.get(i) + 1 != ranks.get(i + 1)){
                return 0;
            }
        }
        return SCORE_500 + getMax(ranks);
    }

    /**
     * Gets the highest value in a list of integers.
     * @param values the integer list.
     * @return the highest value.
     */
    public int getMax(ArrayList<Integer> values){
        int max = values.get(FIRST_CARD_POS);
        for (int i = SECOND_CARD_POSITION; i < values.size(); i++){
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
    /*public static ArrayList<Player> getWinner(ArrayList<Player> players){
        ArrayList<Player> winner = new ArrayList<Player>();
        winner.addAll(players);

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
    }*/



}
    