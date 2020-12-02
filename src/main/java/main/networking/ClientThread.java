/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp, Callie Valenti
 * Section: 01 - 11:30am
 * Date: 11/15/2020
 * Time: 2:16 PM
 *
 * Project: csci205FinalProject
 * Package: main.networking
 * Class: ClientThread
 *
 * Description: A thread for each of the individual clients
 * so that each client can send or receive messages in any order
 *
 * Sources: https://www.youtube.com/watch?v=ZIzoesrHHQo
 *
 * ****************************************
 */
package main.networking;

import main.GUIPlayer;
import main.Table;
import main.pokergamemvc.PokerGameController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable {

    /** Table object */
    private Table table;

    /** Player represented on GUI */
    private GUIPlayer player;

    /** Client username */
    private String userName;

    /** Server username */
    private String hostName;

    /** Server socket */
    private Socket server;

    /** Input stream */
    private BufferedReader in;

    /** Object output stream */
    private ObjectOutputStream objOut;

    /**Object input stream */
    private ObjectInputStream objIn;

    /** GUI Controller */
    private PokerGameController controller;

    /**
     * General constructor for Client Thread
     * @param serverSocket server socket
     * @param userName client username
     * @param hostName server username
     * @param controller GUI controller
     * @param objOut object output stream
     * @param objIn object input stream
     * @throws IOException is thrown if error in connecting to server
     */
    public ClientThread(Socket serverSocket, String userName, String hostName, PokerGameController controller, ObjectOutputStream objOut, ObjectInputStream objIn) throws IOException {
        this.server = serverSocket;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        this.userName = userName;
        this.hostName = hostName;
        this.controller = controller;
        this.objOut = objOut;
        this.objIn = objIn;
    }

    /**
     * Protocol for client thread
     */
    @Override
    public void run() {
        try {

            // Read in table
            table = (Table) objIn.readObject();
            // Create player on GUI from connected client
            player = new GUIPlayer(table.getPlayers().get(Integer.valueOf(hostName)-1));
            player.setUserName(userName);

            // Set the model up for the controller
            controller.setPlayer(player);

            // Set the table up for the controller
            controller.setTable(table);

            // Allow for user input from the keyboard
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()), true);

            while (true) {

                // If it's a player's turn
                if (player.getPlayerNum() == table.getTurn()) {
                    //table.setTurn(table.getTurn() + 1);

                    if (!table.getPlayers().get(player.getPlayerNum()-1).getIsRoundDone().get(table.getBet()) && table.getPlayers().get(player.getPlayerNum()-1).isPlaying) {

                        printToScreen("Enter Go when you have submitted your action: ");

                        table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction(player.getPlayerAction());

                        // Player tries an illegal move
                        if (player.getPlayerAction() == null) {
                            printToScreen("INVALID USER ENTRY: AUTOMATIC FOLD");
                            player.setPlayerAction("Fold");
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Fold");
                            table.getPlayers().get(player.getPlayerNum() - 1).getIsRoundDone().set(table.getBet(), true);
                            table.getPlayers().get(player.getPlayerNum() - 1).isPlaying = false;
                        }
                        // Player bets
                        else if (player.getPlayerAction().equals("Bet")) {
                            table.setBetMin(player.getBet());
                            table.getPot().addToPot(player.getBet());
                            table.getPlayers().get(player.getPlayerNum() - 1).subChips(player.getBet());
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Bet");
                            table.getPlayers().get(player.getPlayerNum() - 1).getIsRoundDone().set(table.getBet(), true);
                            table.getPlayers().get(player.getPlayerNum() - 1).setBet(player.getBet());
                        }
                        // Player checks
                        else if (player.getPlayerAction().equals("Check")) {
                            table.getPot().addToPot(table.getBetMin()-table.getPlayers().get(player.getPlayerNum()-1).getBet());
                            table.getPlayers().get(player.getPlayerNum() - 1).subChips(table.getBetMin());
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Check");
                            table.getPlayers().get(player.getPlayerNum() - 1).getIsRoundDone().set(table.getBet(), true);
                            table.getPlayers().get(player.getPlayerNum() - 1).setBet(table.getBetMin());
                        }
                        // Player folds
                        else if (player.getPlayerAction().equals("Fold")) {
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Fold");
                            ArrayList<Boolean> isRoundDone = new ArrayList<>();
                            isRoundDone.add(true);
                            isRoundDone.add(true);
                            isRoundDone.add(true);
                            isRoundDone.add(true);
                            table.getPlayers().get(player.getPlayerNum() - 1).setIsRoundDone(isRoundDone);
                            table.getPlayers().get(player.getPlayerNum() - 1).isPlaying = false;
                        }
                        // Otherwise player also did illegal move
                        else {
                            printToScreen("INVALID USER ENTRY: AUTOMATIC FOLD");
                            player.setPlayerAction("Fold");
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Fold");
                            table.getPlayers().get(player.getPlayerNum() - 1).getIsRoundDone().set(table.getBet(), true);
                            table.getPlayers().get(player.getPlayerNum() - 1).isPlaying = false;
                        }

                        table.getPlayerActionTexts().set(player.getPlayerNum() - 1, player.playerActionDescription());
                        player.setPlayerAction(null);
                    }

                    // Reset all the object output/input streams
                    objOut.flush();
                    objOut.reset();
                    objOut.writeObject(table);
                    objOut.flush();
                    objOut.reset();
                }
                // Read in table
                table = (Table) objIn.readObject();

                // Set the table up for the controller
                controller.setTable(table);

                // Set all attributes to the correct player
                player.setCard1(table.getPlayers().get(player.getPlayerNum()-1).getPlayerHand().get(0));
                player.setCard2(table.getPlayers().get(player.getPlayerNum()-1).getPlayerHand().get(1));
                player.setChips(table.getPlayers().get(player.getPlayerNum()-1).getChips().getCurrAmount());
                player.setPlayerNum(table.getPlayers().get(player.getPlayerNum()-1).getPlayerNum());
                controller.setPlayer(player);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception in Server Connection");
            e.printStackTrace();
        }
        // Close input/output streams
        finally {
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
     * Prints message on client/side
     * @param msg message to display
     */
    private void printToScreen(String msg) {
        System.out.println(msg);
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
}
