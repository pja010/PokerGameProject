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
package main.networking;

import main.Player;
import main.Round;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameFlowNetworking {

    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;
    private static Scanner scnr = new Scanner(System.in);
    private static BufferedInputStream in;
    private static int PORT = 12224;
    private static boolean isConnecting = true;

//    public static void main(String[] args) {
//
//        initPlayers();
//        setChips();
//
//        networkingSetUp();
//
//        ArrayList<Player> playerList = new ArrayList<>(4);
//        playerList.add(player1);
//        playerList.add(player2);
//        playerList.add(player3);
//        playerList.add(player4);
//
//        // ESTABLISH A CONNECTION BEFORE ROUND STARTS
//
//
//        Round round1 = new Round(playerList);
//        System.out.println("Starting new round.");
////        round1.startRound();
//
//        // WE NEED TO ADD A BOOLEAN FOR EACH PLAYER TO CHECK IF THEY ARE PLAYING
//        // TO CONTROL THIS
//
//        // GETTING PLAYER ACTIONS AND BET AMOUNTS IS EVENTUALLY GOING
//        // TO GO IN AN ORDER BUT FOR NOW LEAVING AS ONE PART
//
//        System.out.println(player1.getChips());
//        System.out.println(player2.getChips());
//        System.out.println(player3.getChips());
//        System.out.println(player4.getChips());
//    }

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

    public static void main(String[] args) throws IOException {
        System.out.println("Please enter your name");
        String userName = scnr.next();
        System.out.println("Will you host or join a game? Enter H or J.");
        String willHost = scnr.next();
        if (willHost.equals("H")) {

            ArrayList<Thread> clients = new ArrayList<>();
            Socket client;
            ServerSocket listener = new ServerSocket(PORT);

            Thread t1;

            System.out.println("Server is waiting for client connection");
            while (isConnecting) {
                client = listener.accept();
                System.out.println("Server connected to client");

                // Creates new thread for each new client
                t1 = new Thread(new ClientHandlerThread(client, userName));
                clients.add(t1);
                t1.start();

                if (clients.size() == 4){
                    isConnecting = false;
                }
            }
            listener.close();

//            ClientHandlerThread clientThread = new ClientHandlerThread(client);
//            clients.add(clientThread);

//            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            // Wait for message from client
//            String name = in.readLine();
//            // Transmit message from server
//            out.println(userName);
//            System.out.println("Connected to : " + name);

//            listener.close();
//            client.close();
        }
        else if (willHost.equals("J")) {
            Socket client = new Socket("134.82.179.74", PORT);
            // Transmit message from client
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            out.println(userName);

            // Get response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Connected to: " + in.readLine());
            client.close();
        }
    }
}
