/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/15/2020
 * Time: 2:16 PM
 *
 * Project: csci205FinalProject
 * Package: main.networking
 * Class: ServerConnection
 *
 * Description: A thread for each of the individual clients
 * so that each client can send or receive messages in any order
 *
 * ****************************************
 */
package main.networking;

import main.PlayerAction;
import main.PlayerCopy;
import main.Table;
import main.pokergamemvc.PokerGameController;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Table table;
    private PlayerCopy player;
    private String userName;
    private String hostName;

    private Socket server;
    private BufferedReader in;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private PokerGameController controller;

    public ClientThread(Socket serverSocket, String userName, String hostName, PokerGameController controller, ObjectOutputStream objOut, ObjectInputStream objIn) throws IOException {
        this.server = serverSocket;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
        //this.table = table;
        //this.player = player;
        this.userName = userName;
        this.hostName = hostName;

        this.controller = controller;

        this.objOut = objOut;
        this.objIn = objIn;


    }

    @Override
    public void run() {
        try {

            System.out.println("Client before readOBj");
            table = (Table) objIn.readObject();
            System.out.println(table.getPot().getTotalAmount());
            System.out.println("Client after readOBj");
            player = new PlayerCopy(table.getPlayers().get(Integer.valueOf(hostName)-1));
            System.out.println(player.getPlayerHand());
            player.setUserName(userName);

            // Set the model up for the controller
            controller.setPlayer(player);

            // Set the table up for the controller
            controller.setTable(table);

            // Allow for user input from the keyboard
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(server.getOutputStream()), true);


            //objOut = new ObjectOutputStream(server.getOutputStream());
            //objIn = new ObjectInputStream((server.getInputStream()));

            printToScreen("entered run loop");


            while (true) {


                if (player.getPlayerNum() == table.getTurn()) {
                    table.setTurn(table.getTurn() + 1);

                    printToScreen("Enter Go when you have submitted your action: ");

                    String clientCommand = keyboard.readLine();
                    // Send message to server
                    out.println(clientCommand);
                    // Receive a message from the server which could be, "Thanks for responding" or the group message
                    String serverResponse = waitForMessage(in);
                    printToScreen(serverResponse);

                    System.out.println(player.getPlayerAction());

                    table.getPlayers().get(player.getPlayerNum()-1).setPlayerAction(player.getPlayerAction());

                    if (player.getPlayerAction().equals("Bet")) {
                        table.setBetMin(player.getBet());
                        table.getPot().addToPot(player.getBet());
                        table.getPlayers().get(player.getPlayerNum()-1).subChips(player.getBet());
                        table.getPlayers().get(player.getPlayerNum()-1).setPlayerAction("Bet");
                        table.getPlayers().get(player.getPlayerNum()-1).getIsRoundDone().set(table.getBet(),true);
                    } else if (player.getPlayerAction().equals("Check")) {
                        table.getPot().addToPot(table.getBetMin());
                        table.getPlayers().get(player.getPlayerNum()-1).setPlayerAction("Check");
                        table.getPlayers().get(player.getPlayerNum()-1).getIsRoundDone().set(table.getBet(),true);
                    } else if (player.getPlayerAction().equals("Fold")) {
                        table.getPlayers().get(player.getPlayerNum()-1).setPlayerAction("Fold");
                        table.getPlayers().get(player.getPlayerNum()-1).getIsRoundDone().set(table.getBet(),true);
                        table.getPlayers().get(player.getPlayerNum()-1).isPlaying = false;
                    }

                    table.getPlayerActionTexts().set(player.getPlayerNum()-1,player.playerActionDescription());

                    printToScreen("PLAYER before writeOBj");
                    System.out.println(table.getPot().getTotalAmount());
                    objOut.flush();
                    objOut.reset();
                    objOut.writeObject(table);
                    printToScreen("PLAYER after writeOBj");
                    objOut.flush();
                    objOut.reset();
                }


                printToScreen("OUT TO ALL before readOBj");
                table = (Table) objIn.readObject();
                System.out.println(table.getPot().getTotalAmount());
                printToScreen("OUT TO ALL after readOBj");

                // Set the table up for the controller
                controller.setTable(table);

                player.setCard1(table.getPlayers().get(player.getPlayerNum()-1).getPlayerHand().get(0));
                player.setCard2(table.getPlayers().get(player.getPlayerNum()-1).getPlayerHand().get(1));

                controller.updateChipsAmountText();

                controller.updatePlayerCards();

                //printToScreen("1Client before writeObj");
                //System.out.println(table.getPot().getTotalAmount());
                //objOut.flush();
                //objOut.writeObject(table);
                //printToScreen("1Client after writeObj");
                //objOut.reset();
                //objOut.flush();

            }
            //this.server.close();
        } catch (IOException | ClassNotFoundException e) {
//            } catch(IOException e) {
            System.out.println("Exception in Server Connection");
            e.printStackTrace();
        } finally {
            try {
                in.close();
                objIn.close();
                objOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printToScreen(String msg) {
        System.out.println(msg);
    }

    public String waitForMessage(BufferedReader in) throws IOException {
        String sBuffer;
        sBuffer = in.readLine();
        return sBuffer;
    }
}
