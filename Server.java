/**
 * File Name     : Server.java
 * Purpose       :
 * Creation Date : 26-10-2013
 * Last Modified : Sat 26 Oct 2013 05:50:41 PM CEST
 * Created By    :
 *
 */


import java.io.*;
import java.net.*;


public class Server {
    public static void main( String[] args ) {
        int portNumber = 1024;

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);

            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            ) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    out.println(inputLine);
                }
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
    }
}
