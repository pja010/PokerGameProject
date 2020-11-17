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
 * Description:
 *
 * ****************************************
 */
package main.networking;

import main.Table;

import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable {

    private Table table;

    private Socket server;
    private BufferedReader in;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    public ServerConnection(Socket serverSocket) throws IOException {
        this.server = serverSocket;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));

    }

    @Override
    public void run() {
            try {
                objOut = new ObjectOutputStream(new BufferedOutputStream(server.getOutputStream()));
                objIn = new ObjectInputStream(new BufferedInputStream(server.getInputStream()));

                while (true) {
                    // Receive a message from the server, the group message being received
                    String serverResponse = in.readLine();
                    if (serverResponse == null) break;

                    System.out.println(serverResponse);
                    if (serverResponse.startsWith("table")){
                        System.out.println("Server before readOBj");
                        table = (Table) objIn.readObject();
                        System.out.println("Server after readOBj");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
