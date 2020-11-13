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
    private ArrayList<Thread> clients;


    public ClientHandlerThread(Socket clientSocket, String userName) throws IOException {
        this.client = clientSocket;
        this.userName = userName;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);
    }

    public void run() {
        try {
            // Wait for message from client
            String clientName = in.readLine();

            // Transmit message from server
            out.println(this.userName);
            System.out.println("Connected to : " + clientName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Oopsie IO Exception");
        }
        finally {
            out.close();;
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}