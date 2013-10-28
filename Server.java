/**
 * File Name     : Server.java
 * Purpose       :
 * Creation Date : 26-10-2013
 * Last Modified : Mon 28 Oct 2013 09:25:25 AM CET
 * Created By    :
 *
 */


import java.io.*;
import java.net.*;


public class Server {
    public static void main( String[] args ) {
        int port = 1024;

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        PrintWriter out = null;
        BufferedReader in = null;

        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Failed to listen on Port: " + port);
            System.exit(1);
        }

        String secret = null;
        String auth = null;

        try {
            secret = in.readLine();
            out.println("Success");

            while ((auth = in.readLine()) != null) {
                System.out.println("Received Hash: " + auth);
                if (auth.equals(secret)) {
                    out.println("Success");
                } else {
                    out.println("Failed");
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to receive Data on Port: " + port);
            System.exit(1);
        }
    }
}
