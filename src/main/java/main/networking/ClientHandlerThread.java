/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp
 * Section: 01 0 11:30am
 * Date: 11/10/2020
 * Time: 2:33 PM
 *
 * Project: csci205FinalProject
 * Package: main.networking
 * Class: ClientHandlerThread
 *
 * Description: https://stackoverflow.com/questions/10131377/socket-programming-multiple-client-to-one-server
 *
 * ****************************************
 */
package main.networking;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import main.Player;
import main.PlayerCopy;
import main.Table;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandlerThread implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scnr = new Scanner(System.in);
    private String userName;
//    private ArrayList<Thread> clients;
    private  ArrayList<ClientHandlerThread> clients;
    private String clientName;
    private String clientResponse;

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Table table;
    private ArrayList<PlayerCopy> players;
    private int playerNum;


    public ClientHandlerThread(Socket clientSocket, String userName, ArrayList<ClientHandlerThread> clients, Table table, int playerNum) throws IOException {
        this.client = clientSocket;
        this.userName = userName;
        this.clients = clients;
        this.table = table;
        this.playerNum = playerNum;

        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);

    }

    @Override
    public void run() {
        try {


            objIn = new ObjectInputStream(client.getInputStream());
            objOut = new ObjectOutputStream(client.getOutputStream());


            while (true) {

                if (table.getTurn() == playerNum) {
                    printToScreen(String.valueOf(playerNum) + " : " + String.valueOf(table.getTurn()));

                    // Response from client
                    clientResponse = waitForMessage(in);
                    transmitMessage(out, clientResponse);

                    printToScreen("PLAYER before readOBj");
                    table = (Table) objIn.readObject();
                    System.out.println(table.getPot().getTotalAmount());
                    printToScreen("PLAYER after readOBj");


                    boolean roundOver = true;
                    for (Player player: table.getPlayers()) {
                        if (!player.getIsRoundDone().get(table.getBet())){
                            roundOver = false;
                        }
                    }

                    if (roundOver) {
                        if (table.getBet() == 3){
                            ArrayList<Player> winners = table.getWinner();
                            for (Player player: winners){
                                player.addChips(table.getPot().getTotalAmount()/winners.size());
                            }
                            table.getPot().setTotalAmount(0);
                            table.getTableCards().clear();
                            initPlayers();
                            setChips();
                            table.setPlayerCards();
                            table.setTableCards();
                            table.setBetMin(1);
                            table.setBet(0);
                            table.setTurn(1);
                            table.setRound(table.getRound()+1);
                        }
                        else {
                            table.setTurn(1);
                            table.setBet(table.getBet() + 1);
                        }
                    }

                    printToScreen("OUT TO ALL before writeOBj");
                    System.out.println(table.getPot().getTotalAmount());
                    objOutToAll();
                    objOut.reset();
                    printToScreen("OUT TO ALL after writeOBj");
                }


                printToScreen("2ClientHandler before writeOBj");
                table = (Table) objIn.readObject();
                System.out.println(table.getPot().getTotalAmount());
                printToScreen("2ClientHandler after writeOBj");

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Oopsie IO Exception");
        }
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

    private void outToAll(String msg) throws IOException {
        for (ClientHandlerThread aClient : clients) {
            aClient.out.println(clientName + " says " + msg);
            objOut.flush();
        }
    }

    private void objOutToAll() throws IOException {
        for (ClientHandlerThread aClient : clients) {
            aClient.objOut.writeObject(table);
            objOut.flush();
        }
    }

    private void printToScreen(String msg) {
        System.out.println(msg);
    }

    private void transmitMessage(PrintWriter out, String message) {
        // Send as a sequence of bytes by using the getBytes
        out.println(message);
    }

    public String waitForMessage(BufferedReader in) throws IOException {
        String sBuffer;
        sBuffer = in.readLine();
        return sBuffer;
    }

    public void initCards(){
        players.get(playerNum).setCard1(table.getDeck().deal());
        players.get(playerNum).setCard2(table.getDeck().deal());
    }

    private void initPlayers() {
        table.getPlayers().set(0, new Player(1));
        table.getPlayers().set(1, new Player(2));
        table.getPlayers().set(2, new Player(3));
        table.getPlayers().set(3, new Player(4));
    }

    private void setChips() {
        table.getPlayers().get(0).setChips(1600);
        table.getPlayers().get(1).setChips(1600);
        table.getPlayers().get(2).setChips(1600);
        table.getPlayers().get(3).setChips(1600);
    }
}