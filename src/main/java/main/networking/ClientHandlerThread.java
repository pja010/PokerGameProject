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


    public ClientHandlerThread(Socket clientSocket, String userName, ArrayList<ClientHandlerThread> clients, Table table) throws IOException {
        this.client = clientSocket;
        this.userName = userName;
        this.clients = clients;
        this.table = table;

        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);

    }

    @Override
    public void run() {
        try {
            // Wait for message from client
            clientName = waitForMessage(in);

            // Transmit message from server
            transmitMessage(out, this.userName);

            printToScreen("Connected to : " + clientName);

            objOut = new ObjectOutputStream(client.getOutputStream());
            objIn = new ObjectInputStream(client.getInputStream());

            while (true) {
                // Response from client
                clientResponse = waitForMessage(in);

                if (clientResponse.equals("quit")){
                    transmitMessage(out,"quit");
                    printToScreen(clientName + " has disconnected!");
                    objOut.reset();
                    break;
                }
                // Client wants to share message to everyone
                else if (clientResponse.startsWith("say")){
                    int firstSpace = clientResponse.indexOf(" ");
                    outToAll(clientResponse.substring(firstSpace + 1));
                    printToScreen("Group message: " + clientResponse.substring(firstSpace + 1));
                }
                // Client wants to view the table
                else if (clientResponse.startsWith("table")){
                    transmitMessage(out, "table");
                    printToScreen("ClientHandler before readOBj");
                    objOut.writeObject(table);
                    printToScreen("ClientHandler after readOBj");
                }
                // Otherwise, just send to server
                else {
                    printToScreen(clientName + " says " + clientResponse);
                    transmitMessage(out,"Thanks for responding, " + clientName );
                }

            }

        } catch (IOException e) {
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
}