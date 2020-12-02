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

    private Table table;
    private GUIPlayer player;
    private String userName;
    private String hostName;

    private Socket server;
    private BufferedReader in;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private PokerGameController controller;

    public ClientThread(Socket serverSocket, String userName, String hostName, PokerGameController controller, ObjectOutputStream objOut, ObjectInputStream objIn, BufferedReader in) throws IOException {
        this.server = serverSocket;
        this.in = in;
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

            //System.out.println("Client before readOBj");
            table = (Table) objIn.readObject();
            //System.out.println(table.getPot().getTotalAmount());
            //System.out.println("Client after readOBj");
            player = new GUIPlayer(table.getPlayers().get(Integer.valueOf(hostName)-1));
            //System.out.println(player.getPlayerHand());
            player.setUserName(userName);

            // Set the model up for the controller
            controller.setPlayer(player);

            // Set the table up for the controller
            controller.setTable(table);


            //objOut = new ObjectOutputStream(server.getOutputStream());
            //objIn = new ObjectInputStream((server.getInputStream()));

            //printToScreen("entered run loop");


            while (true) {


                if (player.getPlayerNum() == table.getTurn()) {
                    //table.setTurn(table.getTurn() + 1);

                    if (!table.getPlayers().get(player.getPlayerNum()-1).getIsRoundDone().get(table.getBet()) && table.getPlayers().get(player.getPlayerNum()-1).isPlaying) {

                        printToScreen("Enter Go when you have submitted your action: ");

                        String clientCommand = in.readLine();
                        // Send message to server
                        //out.println(clientCommand);
                        // Receive a message from the server which could be, "Thanks for responding" or the group message
                        //String serverResponse = waitForMessage(in);
                        //printToScreen(clientCommand);

                        //System.out.println(player.getPlayerAction());

                        table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction(player.getPlayerAction());

                        if (player.getPlayerAction() == null) {
                            printToScreen("INVALID USER ENTRY: AUTOMATIC FOLD");
                            player.setPlayerAction("Fold");
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Fold");
                            table.getPlayers().get(player.getPlayerNum() - 1).getIsRoundDone().set(table.getBet(), true);
                            table.getPlayers().get(player.getPlayerNum() - 1).isPlaying = false;
                        }
                        else if (player.getPlayerAction().equals("Bet")) {
                            table.setBetMin(player.getBet());
                            table.getPot().addToPot(player.getBet());
                            table.getPlayers().get(player.getPlayerNum() - 1).subChips(player.getBet());
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Bet");
                            table.getPlayers().get(player.getPlayerNum() - 1).getIsRoundDone().set(table.getBet(), true);
                            table.getPlayers().get(player.getPlayerNum() - 1).setBet(player.getBet());
                        } else if (player.getPlayerAction().equals("Check")) {
                            table.getPot().addToPot(table.getBetMin()-table.getPlayers().get(player.getPlayerNum()-1).getBet());
                            table.getPlayers().get(player.getPlayerNum() - 1).subChips(table.getBetMin());
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Check");
                            table.getPlayers().get(player.getPlayerNum() - 1).getIsRoundDone().set(table.getBet(), true);
                            table.getPlayers().get(player.getPlayerNum() - 1).setBet(table.getBetMin());
                        } else if (player.getPlayerAction().equals("Fold")) {
                            table.getPlayers().get(player.getPlayerNum() - 1).setPlayerAction("Fold");
                            ArrayList<Boolean> isRoundDone = new ArrayList<>();
                            isRoundDone.add(true);
                            isRoundDone.add(true);
                            isRoundDone.add(true);
                            isRoundDone.add(true);
                            table.getPlayers().get(player.getPlayerNum() - 1).setIsRoundDone(isRoundDone);
                            table.getPlayers().get(player.getPlayerNum() - 1).isPlaying = false;
                        }
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

                    //printToScreen("PLAYER before writeOBj");
                    //System.out.println(table.getPot().getTotalAmount());
                    objOut.flush();
                    objOut.reset();
                    objOut.writeObject(table);
                    //printToScreen("PLAYER after writeOBj");
                    objOut.flush();
                    objOut.reset();
                }


                //printToScreen("OUT TO ALL before readOBj");
                table = (Table) objIn.readObject();
                //System.out.println(table.getPot().getTotalAmount());
                //printToScreen("OUT TO ALL after readOBj");

                // Set the table up for the controller
                controller.setTable(table);

                player.setCard1(table.getPlayers().get(player.getPlayerNum()-1).getPlayerHand().get(0));
                player.setCard2(table.getPlayers().get(player.getPlayerNum()-1).getPlayerHand().get(1));

                player.setChips(table.getPlayers().get(player.getPlayerNum()-1).getChips().getCurrAmount());

                player.setPlayerNum(table.getPlayers().get(player.getPlayerNum()-1).getPlayerNum());

                controller.setPlayer(player);


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
