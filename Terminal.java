/**
 * File Name     : Terminal.java
 * Purpose       :
 * Creation Date : 23-10-2013
 * Last Modified : Sun 27 Oct 2013 05:02:18 PM CET
 * Created By    :
 *
 */


import java.io.*;
import java.net.*;


/* BufferedReader
   PrintWriter
*/

public class Terminal {
    public static void main(String[] args) throws IOException {

        String hostname = "localhost";
        int portNumber = 1024;

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(hostname, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for: " + hostname);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String userInput;

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println(in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
