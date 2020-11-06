/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti
 * Section: 11:30
 * Date: 11/2/20
 * Time: 5:49 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: GameFlow
 *
 * Description:
 *
 * ****************************************
 */
package main;

import java.util.ArrayList;

public class GameFlow {

    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;

    public static void main(String[] args) throws EmptyDeckException {

        initPlayers();
        setChips();

        ArrayList<Player> playerList = new ArrayList<>(4);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);

        Round round1 = new Round(playerList);
        System.out.println("Starting new round.");
        round1.startRound();

        // WE NEED TO ADD A BOOLEAN FOR EACH PLAYER TO CHECK IF THEY ARE PLAYING
        // TO CONTROL THIS

        // GETTING PLAYER ACTIONS AND BET AMOUNTS IS EVENTUALLY GOING
        // TO GO IN AN ORDER BUT FOR NOW LEAVING AS ONE PART

        System.out.println(player1.getChips());
        System.out.println(player2.getChips());
        System.out.println(player3.getChips());
        System.out.println(player4.getChips());
    }

       private static void initPlayers() {
        player1 = new Player(1);
        player2 = new Player(2);
        player3 = new Player(3);
        player4 = new Player(4);
    }

    private static void setChips() {
        player1.setChips(1600);
        player2.setChips(1600);
        player3.setChips(1600);
        player4.setChips(1600);
    }
}
    