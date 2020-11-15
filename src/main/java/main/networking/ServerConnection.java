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

import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private Socket server;
    private BufferedReader in;

    public ServerConnection(Socket serverSocket) throws IOException {
        this.server = serverSocket;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    @Override
    public void run() {
            try {
                while (true) {
                    // Receive a message from the server
                    String serverResponse = in.readLine();
                    if (serverResponse == null) {break;}
                    System.out.println(serverResponse);
                }
            } catch (IOException e) {
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
