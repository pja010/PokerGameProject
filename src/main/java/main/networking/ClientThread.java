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

import main.Table;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Table table;

    private Socket server;
    private BufferedReader in;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    public ClientThread(Socket serverSocket) throws IOException {
        this.server = serverSocket;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));

    }

    @Override
    public void run() {
            try {
                objOut = new ObjectOutputStream(server.getOutputStream());
                objIn = new ObjectInputStream((server.getInputStream()));

                while (true) {
                    // Receive a message from the server which could be, "Thanks for responding" or the group message
                    String serverResponse = waitForMessage(in);
                    if (serverResponse.equals("quit")) {
                        printToScreen("Thanks for connecting!");
                        break;
                    }
                    else if (serverResponse.startsWith("table")){
                        printToScreen("Client before readOBj");
                        table = (Table) objIn.readObject();
                        System.out.println(table.getPot().getTotalAmount());
                        printToScreen("Client after readOBj");
                    }
                    printToScreen(serverResponse);
                }
                this.server.close();
            } catch (IOException | ClassNotFoundException e) {
//            } catch(IOException e) {
                System.out.println("Exception in Server Connection");
                e.printStackTrace();
            } finally {
                try {
                    in.close();
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
