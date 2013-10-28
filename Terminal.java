/**
 * File Name     : Terminal.java
 * Purpose       :
 * Creation Date : 23-10-2013
 * Last Modified : Mon 28 Oct 2013 09:08:19 PM CET
 * Created By    :
 *
 */

/*
 * Verbindet sich mit Server auf Port 1024
 * wartet auf eine Eingabe an der Tastatur, diese wird als Hash (4 Byte)
 * an den Server gesendet und dort als secret abgespeichert.
 * Nach der Tastatureingabe, wird auf Port 1300 auf ein Kennwort gewartet
 * und den zugehoerigen Hash an den Server gesendet, das Ergebnis wird 
 * wird an den Client (Attacker) zurueckgesendet.
 */

import java.io.*;
import java.net.*;

public class Terminal {
    public static void main(String[] args) {

        String hostname = "localhost";
        int port = 1024;

        Socket clientSocket = null;
        PrintWriter clientOut = null;
        BufferedReader clientIn = null;

        BufferedReader stdIn = null;

/* Server 
        ServerSocket serverSocket = null;
        Socket receiveSocket = null;
        PrintWriter serverOut = null;
        BufferedReader serverIn = null;

        try {
            serverSocket = new ServerSocket(1300);
        } catch (IOException e) {
            System.err.println("Couldn't listen on port: 1300");
            System.exit(1);
        }

        try {
            receiveSocket = serverSocket.accept();
            serverOut = new PrintWriter(receiveSocket.getOutputStream(), true);
            serverIn = new BufferedReader(new InputStreamReader(receiveSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Accept failed");
            System.exit(1);
        }
 Server */

/* Client */
        try {
            clientSocket = new Socket(hostname, port);
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for: " + hostname);
            System.exit(1);
        }

/* User Keyboard Input */
        String userInput;
        HashPass auth;

        try {
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            while ((userInput = stdIn.readLine()) != null) {
                auth = new HashPass(userInput);
                clientOut.println(auth.getHash());
                System.out.println(clientIn.readLine());
            }
        } catch (IOException e) {
            System.exit(1);
        }

        /*
        while ((userInput = stdIn.readLine()) != "\n") {
            auth = new HashPass(userInput);
            clientOut.println(auth.getHash());
            System.out.println(clientIn.readLine());
        }
        */

/* Online Input 
        String attackerInput;

        System.out.println("Online Attackphase");
        attackerInput = serverIn.readLine();
        auth = new HashPass(attackerInput);
        clientOut.println(auth.getHash());
        if (clientIn.readLine() == "Success") {
            serverOut.println("Success");
        } else {
            System.out.println("Failed");
            serverOut.println("Failed");
        }
        */
        

        /*
        while ((attackerInput = serverIn.readLine()) != null) {
            System.out.println("Online Attackphase");
            auth = new HashPass(attackerInput);
            clientOut.println(auth.getHash());
            if (clientIn.readLine() == "Success") {
                serverOut.println("Success");
            } else {
                System.out.println(clientIn);
            }
        }
        */

        try {
            clientOut.close();
            clientIn.close();
            stdIn.close();
            clientSocket.close();
        } catch (IOException e) {
            System.exit(1);
        }
/*
        serverIn.close();
        serverOut.close();
        receiveSocket.close();
        serverSocket.close();
        */
    }
}
