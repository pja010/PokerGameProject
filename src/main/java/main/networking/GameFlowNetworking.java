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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameFlowNetworking {

    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;
    private static Scanner scnr = new Scanner(System.in);
    private static BufferedInputStream in;
    private static int PORT = 12224;
    private static boolean isConnecting = true;
    private static ArrayList<ClientHandlerThread> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static InetAddress address = null;


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

            try {
                address = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                System.out.println("Oops");
            }
            System.out.println("" + address.getHostAddress());


            Socket client;
            ServerSocket listener = new ServerSocket(PORT);

            Thread t1;

            System.out.println("Server is waiting for client connection");
            while (isConnecting) {
                client = listener.accept();
                System.out.println("Server connected to client");

                ClientHandlerThread clientThread = new ClientHandlerThread(client, userName, clients);
                clients.add(clientThread);

                pool.execute(clientThread);

//                if (clients.size() == 4){
//                    isConnecting = false;
//                }
            }
            listener.close();
        }
        else if (willHost.equals("J")) {
            System.out.println("Please enter Host address");
            String hostAddress = scnr.next();
            Socket client = new Socket(hostAddress,PORT);

//            Socket client = new Socket("134.82.179.74", PORT);

            // Transmit message from client to server
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
            out.println(userName);

            // Get response from server
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Connected to: " + in.readLine());

            // NEW STUFF
            ServerConnection serverConnection = new ServerConnection(client);

            // Allow for user input from the keyboard
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


            while (true){
                System.out.print("> ");
                String clientCommand = keyboard.readLine();

                if (clientCommand.equals("quit")) {
                    out.println(clientCommand);
                    break;
                }
                // Send message to server
                out.println(clientCommand);


                //NEW STUFF
                new Thread(serverConnection).start();

                //THIS IS THE STUFF I'M EDITING
                // Receive message from server
//                String serverResponse = in.readLine();
//                System.out.println(serverResponse);
            }
            client.close();
        }
    }
}
