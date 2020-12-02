/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King *
 * Name: Callie Valenti, Lindsay Knupp, Per Astrom, Guillermo Tores
 * Section: 01 - 11:30am
 * Date: 11/2/20
 * Time: 5:49 PM
 *
 * Project: csci205FinalProject
 * Package: main * Class: GameFlow
 *
 * Description: Controls game flow of entire
 * Poker Game
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
import main.GUIPlayer;
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

    /** Four players in Poker game  */
    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;

    /** List of players to be used for GUI  */
    private static ArrayList<GUIPlayer> players;

    /** Scanner */
    private static Scanner scnr = new Scanner(System.in);

    /** Port number */
    private static int PORT = 12227;

    /** Controls whether server allows connections */
    private static boolean isConnecting = true;

    /** List of all connected clients */
    private static ArrayList<ClientHandlerThread> clients = new ArrayList<>();

    /** Allows thread to be run  */
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    /** Server IP address */
    private static InetAddress address = null;

    /** Player used for GUI */
    private static GUIPlayer player;

    /** Table object */
    private static Table table;

    /** Controller */
    private static PokerGameController controller;

    /** Allows JavaFX to be run */
    private static FXMLLoader loader;
    private static Parent root;


    /**
     * Initializes players
     */
    private static void initPlayers() {
        player1 = new Player(1);
        player2 = new Player(2);
        player3 = new Player(3);
        player4 = new Player(4);
    }

    /**
     * Sets initial chip amounts
     */
    private static void setChips() {
        player1.setChips(1600);
        player2.setChips(1600);
        player3.setChips(1600);
        player4.setChips(1600);
    }

    /**
     * Main method to run game
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        setUpNetworking(args);
    }

    /**
     * Determines whether user is server or client
     * @param args
     * @throws IOException if problem connecting to Socket
     * @throws ClassNotFoundException
     */
    private static void setUpNetworking(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Please enter your name");
        String userName = scnr.next();
        System.out.println("Will you host or join a game? Enter H or J.");
        String willHost = scnr.next();
        // Person is host
        if (willHost.equals("H") | willHost.equals("h")) {
            initServer(userName);
        }
        // Person is client
        else if (willHost.equals("J") | willHost.equals("j")) {
            initClient(userName, args);
        }
    }

    /**
     * Initializes new client
     * @param userName client username
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static void initClient(String userName, String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Please enter Host address");
        String hostAddress = scnr.next();
        // Create new client socket connected to server socket
        Socket client = new Socket(hostAddress,PORT);

        // Object input/output streams
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

            // Load FXML file
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

    /**
     * Initializes server
     * @param userName server username
     * @throws IOException
     */
    private static void initServer(String userName) throws IOException {
        // Creates players and sets chips
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

            // Object input/output streams
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

    /**
     * Get IP address of server computer
     */
    private static void getAddress() {
        // Displays host's IP address to screen
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Oops");
        }
        System.out.println("" + address.getHostAddress());
    }

    /**
     * Start method for GUI of table
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Poker: Texas Hold'em");
        primaryStage.setScene(new Scene(root, 1570, 800));
        primaryStage.show();
    }

    /**
     * Initializes GUI
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
    }
}
