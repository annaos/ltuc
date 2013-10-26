/**
 * File Name     : Terminal.java
 * Purpose       :
 * Creation Date : 23-10-2013
 * Last Modified : Sat 26 Oct 2013 05:39:15 PM CEST
 * Created By    :
 *
 */


import java.io.*;
import java.net.*;


public class Terminal {
    public static void main(String[] args) {
        String hostName = "localhost";
        int portNumber = 1024;

        try (
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
