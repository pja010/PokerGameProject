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

        objOut = new ObjectOutputStream(client.getOutputStream());
        objIn = new ObjectInputStream(client.getInputStream());
    }

    @Override
    public void run() {
        try {


            while (true) {
                // Response from client
                clientResponse = waitForMessage(in);
                transmitMessage(out, clientResponse);

                if (clientResponse.startsWith("table")) {
                    printToScreen("ClientHandler before readOBj");
                    table = (Table) objIn.readObject();
                    printToScreen("ClientHandler after readOBj");

                    printToScreen("ClientHandler before writeOBj");
                    objOutToAll();
                    printToScreen("ClientHandler after writeOBj");
                }



                System.out.println(table.getTurn());

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Oopsie IO Exception");
        }
        finally {
            out.close();
            try {
                in.close();
//                objOut.reset();
                objIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void outToAll(String msg) {
        for (ClientHandlerThread aClient : clients) {
            aClient.out.println(clientName + " says " + msg);
        }
    }

    private void objOutToAll() throws IOException {
        for (ClientHandlerThread aClient : clients) {
            aClient.objOut.writeObject(table);
            //objOut.reset();
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
}