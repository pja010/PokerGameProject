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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Player;
import main.PlayerCopy;
import main.Table;
import main.pokergamemvc.PokerGameController;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameFlowNetworking extends Application{

    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;
    private static ArrayList<PlayerCopy> players;
    private static Scanner scnr = new Scanner(System.in);
    private static int PORT = 12226;
    private static boolean isConnecting = true;
    private static ArrayList<ClientHandlerThread> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);
    private static InetAddress address = null;

    private static PlayerCopy player;
    private static Table table;
    private static PokerGameController controller;
    private static FXMLLoader loader;
    private static Parent root;


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


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        setUpNetworking(args);
    }

    private static void setUpNetworking(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Please enter your name");
        String userName = scnr.next();
        System.out.println("Will you host or join a game? Enter H or J.");
        String willHost = scnr.next();
        if (willHost.equals("H") | willHost.equals("h")) {
            initServer(userName);
        }
        else if (willHost.equals("J") | willHost.equals("j")) {
            initClient(userName, args);
        }
    }

    private static void initClient(String userName, String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Please enter Host address");
        String hostAddress = scnr.next();
        // Create new client socket connected to server socket
        Socket client = new Socket(hostAddress,PORT);

        ObjectOutputStream objOut = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream objIn = new ObjectInputStream((client.getInputStream()));


        // Transmit message from client to server
        PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
        out.println(userName);

        // Get response from server
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String hostName = in.readLine();
        System.out.println("Connected to: " + hostName);

        while(true){


            loader = new FXMLLoader();
            loader.setLocation(GameFlowNetworking.class.getResource("/PokerGameView.fxml"));
            root = loader.load();

            // Retrieve the controller from the FXML
            controller = loader.getController();


            // Client thread that allows messages to be sent and received in any particular order
            ClientThread serverConnection = new ClientThread(client, userName, hostName, controller, objOut,objIn);
            // Start thread
            new Thread(serverConnection).start();

            launch(args);


        }


    }

    private static void initServer(String userName) throws IOException {
        initPlayers();
        setChips();

        // Display host's IP address to screen
        getAddress();

        ServerSocket listener = new ServerSocket(PORT);

        System.out.println("Server is waiting for client connection");
        Table table = new Table();
        table.addPlayer(player1);
        table.addPlayer(player2);
        table.addPlayer(player3);
        table.addPlayer(player4);

        table.setPlayerCards();
        table.setTableCards();

        Socket client;

        int i = 0;
        while (isConnecting) {
            i += 1;
            // Connection is established
            client = listener.accept();
            System.out.println("Server connected to client");


            ObjectOutputStream objOut = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(client.getInputStream());

            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            // Wait for message from client
            String clientName = in.readLine();;


            // Transmit message from server
            out.println(String.valueOf(i));

            System.out.println("Connected to : " + clientName);



            // Create Server thread responsible for keeping track of all client threads
            ClientHandlerThread clientThread = new ClientHandlerThread(client, userName, clients, table, i, objOut, objIn);
            clients.add(clientThread);

            // Run the threads
            pool.execute(clientThread);
        }
        listener.close();
    }

    private static void getAddress() {
        // Displays host's IP address to screen
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Oops");
        }
        System.out.println("" + address.getHostAddress());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("/PokerGameView.fxml"));
        //Parent root = loader.load();

        // Retrieve the controller from the FXML
        //controller = loader.getController();

        // Set the model up for the controller
        //controller.setPlayer(player);

        // Set the table up for the controller
        //controller.setTable(table);

        primaryStage.setTitle("Poker: Texas Hold'em");
        primaryStage.setScene(new Scene(root, 1570, 800));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }
}
