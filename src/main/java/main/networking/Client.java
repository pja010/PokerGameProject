/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lindsay Knupp
 * Section: 01 - 11:30am
 * Date: 11/8/2020
 * Time: 1:17 PM
 *
 * Project: csci205FinalProject
 * Package: main.networking
 * Class: Client
 *
 * Description:
 *
 * ****************************************
 */
package main.networking;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final int PORT = 12346;
    Socket socket;

    /**
     * An object which handles the client side networking
     * @see <a>https://moodle.bucknell.edu/mod/page/view.php?id=955937</a>
     *
     * @param hostAddress - The address of the host to be connected to.
     * @author Lindsay Knupp, Andrew Whitig
     */
    public Client(String hostAddress) {
        setHostAddress(hostAddress);
    }

    /**
     * Establishes a connection to the host's server socket
     *
     * @param address - The host's internet address
     * @author Lindsay Knupp
     */
    public void setHostAddress(String address){
        try {
            socket = new Socket(address, PORT);
        }
        catch(IOException e){
            System.out.println("Invalid host address.");
        }
    }

    /**
     * Sends and gets the host and joinee usernames
     *
     * @param userName - The joinee username
     * @return A message containing the username of the host indicating that a connection has been made
     * @author Andrew Whitig
     */
    public String exchangeNames(String userName){
        String s = "";
        try {
            transmitMessage(socket, userName);
            s += receiveResponse(socket);
        }
        catch(IOException e) {
            System.out.println("Unable to transmit message.");
        }
        return "Connected to: " + s;
    }


    /**
     * Taken from class
     * @see <a>https://moodle.bucknell.edu/mod/page/view.php?id=955937</a>
     *
     * Transmit message to the server as a {@link String} encoded into bytes
     *
     * @param socket is the socket we're using to send to
     * @param message the message to send
     */
    public static void transmitMessage(Socket socket, String message) throws IOException {
        // Send as a sequence of bytes by using the getBytes
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        out.println(message);
    }

    /**
     * Taken from class
     * <a>https://moodle.bucknell.edu/mod/page/view.php?id=955937</a>
     *
     * Receive a response from the server.
     *
     * @param socket is the socket we're receiving from
     * @return the String that was received
     */
    public static String receiveResponse(Socket socket) throws IOException {
        String sBuffer;
        // Set an infinite time out until we receive some data...
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        sBuffer = in.readLine();
        return sBuffer;
    }
}
