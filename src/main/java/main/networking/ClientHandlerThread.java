/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp, Callie Valenti
 * Section: 01 - 11:30am
 * Date: 11/10/2020
 * Time: 2:33 PM
 *
 * Project: csci205FinalProject
 * Package: main.networking
 * Class: ClientHandlerThread
 *
 * Description: Thread which controls functionality
 * for Server
 *
 * Sources: https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
 * https://www.youtube.com/watch?v=ZIzoesrHHQo
 *
 * ****************************************
 */
package main.networking;

import main.Player;
import main.GUIPlayer;
import main.Table;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandlerThread implements Runnable {
    /** Client socket */
    private Socket client;

    /** Input stream */
    private BufferedReader in;

    /** Output stream */
    private PrintWriter out;

    /** Scanner */
    private Scanner scnr = new Scanner(System.in);

    /** Client username */
    private String userName;

    /** List of all connected clients */
    private  ArrayList<ClientHandlerThread> clients;

    /** Information passed from client to server */
    private String clientName;
    private String clientResponse;

    /** Object output stream */
    private ObjectOutputStream objOut;

    /** Object input stream */
    private ObjectInputStream objIn;

    /** Table object */
    private Table table;

    /** List of players connected to the GUI */
    private ArrayList<GUIPlayer> players;

    /** Player number */
    private int playerNum;

    /**
     * General constructor for ClientHandler Thread
     * @param clientSocket client socket
     * @param userName server's username
     * @param clients updated list of all connected clients
     * @param table gameplay table
     * @param playerNum player number
     * @param objOut object output stream
     * @param objIn object input stream
     * @throws IOException is thrown if error occurs in connecting clients
     */
    public ClientHandlerThread(Socket clientSocket, String userName, ArrayList<ClientHandlerThread> clients, Table table, int playerNum , ObjectOutputStream objOut, ObjectInputStream objIn) throws IOException {
        this.client = clientSocket;
        this.userName = userName;
        this.clients = clients;
        this.table = table;
        this.playerNum = playerNum;

        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);

        this.objOut = objOut;
        this.objIn = objIn;
    }

    /**
     * Protocol for Server thread
     */
    @Override
    public void run() {
        try {

            // Transmit Table
            objOut.writeObject(table);

            // Gameplay is occurring
            while (true) {

                // It is a player's turn
                if (table.getTurn() == playerNum) {
                    printToScreen("Waiting for: " + String.valueOf(table.getTurn()));

                    // Read in Table object
                    table = (Table) objIn.readObject();

                    for (Player player: table.getPlayers()){
                        // If player tried to bet less than minimum bet
                        if (player.isPlaying && player.getBet() < table.getBetMin()){
                            player.getIsRoundDone().set(table.getBet(),false);
                        }
                    }

                    boolean roundOver = true;
                    // If not all players have completed their turn
                    for (Player player: table.getPlayers()) {
                        if (!player.getIsRoundDone().get(table.getBet())){
                            roundOver = false;
                        }
                    }

                    // Round is over
                    if (roundOver) {
                        table.getPlayerActionTexts().set(0,"");
                        table.getPlayerActionTexts().set(1,"");
                        table.getPlayerActionTexts().set(2,"");
                        table.getPlayerActionTexts().set(3,"");

                        // Get winner
                        if (table.getBet() == 3){
                            ArrayList<Player> winners = new ArrayList<>();
                            winners = table.getPlayers();
                            ArrayList<Player> winners1 = table.getWinner(winners);
                            for (Player player: winners1){
                                player.addChips(table.getPot().getTotalAmount()/winners1.size());
                                table.getPlayers().get(player.getPlayerNum()-1).addChips(table.getPot().getTotalAmount()/winners1.size());
                                table.getPlayerActionTexts().set(player.getPlayerNum()-1,String.valueOf(player.getPlayerNum()) + " is a Winner!");
                            }
                            // Start a new round
                            for (Player player:table.getPlayers()){
                                player.getIsRoundDone().clear();
                                player.getIsRoundDone().add(false);
                                player.getIsRoundDone().add(false);
                                player.getIsRoundDone().add(false);
                                player.getIsRoundDone().add(false);
                                player.isPlaying = true;
                                if(player.getPlayerNum() == 4){
                                    player.setPlayerNum(1);
                                }
                                else{
                                    player.setPlayerNum(player.getPlayerNum()+1);
                                }
                            }
                            // Clear everything
                            table.getPot().setTotalAmount(0);
                            table.getTableCards().clear();
                            for (Player player: table.getPlayers()){
                                player.getPlayerHand().clear();
                            }
                            table.setPlayerCards();
                            table.setTableCards();
                            table.setBetMin(0);
                            table.setBet(0);
                            table.setTurn(1);
                            table.setRound(table.getRound()+1);
                        }
                        else {
                            table.setTurn(1);
                            table.setBet(table.getBet() + 1);
                            table.setBetMin(0);
                        }
                    }
                    else if (table.getTurn()==4){
                        table.setTurn(1);
                    }
                    else{
                        table.setTurn(table.getTurn()+1);
                    }

                    System.out.println(table.getPot().getTotalAmount());
                    objOutToAll();
                    objOut.reset();
                }

                else if (table.getTurn() == 4){
                    table.setTurn(1);
                }
                else{
                    table.setTurn(table.getTurn()+1);
                }

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Oopsie IO Exception");
        }
        // Close all of the input/output streams
        finally {
            out.close();
            try {
                in.close();
                objIn.close();
                objOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a message out to all connected clients
     * @param msg message to send
     * @throws IOException is thrown if error with Sockets
     */
    private void outToAll(String msg) throws IOException {
        for (ClientHandlerThread aClient : clients) {
            aClient.out.println(clientName + " says " + msg);
            objOut.flush();
        }
    }

    /**
     * Sends an object out to all connected clients
     * @throws IOException is thrown if error with Sockets
     */
    private void objOutToAll() throws IOException {
        for (ClientHandlerThread aClient : clients) {
            aClient.objOut.writeObject(table);
            objOut.flush();
        }
    }

    /**
     * Prints message on server/side
     * @param msg message to display
     */
    private void printToScreen(String msg) {
        System.out.println(msg);
    }

    /**
     * Transmits message from server to client
     * @param out output stream
     * @param message message to be sent
     */
    private void transmitMessage(PrintWriter out, String message) {
        // Send as a sequence of bytes by using the getBytes
        out.println(message);
    }

    /**
     * Waits for message from client
     * @param in input stream
     * @return transmitted message
     * @throws IOException is thrown if message with Sockets
     */
    public String waitForMessage(BufferedReader in) throws IOException {
        String sBuffer;
        sBuffer = in.readLine();
        return sBuffer;
    }

    /**
     * Initializes cards
     */
    public void initCards(){
        players.get(playerNum).setCard1(table.getDeck().deal());
        players.get(playerNum).setCard2(table.getDeck().deal());
    }

    /**
     * Initializes players
     */
    private void initPlayers() {
        table.getPlayers().set(0, new Player(1));
        table.getPlayers().set(1, new Player(2));
        table.getPlayers().set(2, new Player(3));
        table.getPlayers().set(3, new Player(4));
    }

    /**
     * Set chips
     */
    private void setChips() {
        table.getPlayers().get(0).setChips(1600);
        table.getPlayers().get(1).setChips(1600);
        table.getPlayers().get(2).setChips(1600);
        table.getPlayers().get(3).setChips(1600);
    }
}
