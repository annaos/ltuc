/**
 * File Name     : Attacker.java
 * Purpose       :
 * Creation Date : 23-10-2013
 * Last Modified : Tue 29 Oct 2013 03:26:09 PM CET
 * Created By    :
 *
 */

/*
 * 
 * Szenario:
 * Wir wollen ein einfaches, passwortbasiertes Zugangssystem angreifen. Das Zugangssystem
 * funktioniert wie folgt:
 * 
 *    1. Der Nutzer, der sich authentifizieren will, gibt an einem gesicherten Terminal sein
 *       Passwort ein. Der String wird mit Hilfe der kryptographischen Hashfunktion SHA-1
 *       gehasht. Verwendet werden allerdings nur die letzten 4 Byte.
 *    2. Das Terminal sendet diesen sogenannten Message Digest zum Authentifikationsser-
 *       ver. Dieser vergleicht den erhaltenen Message Digest mit dem Wert, den er für die-
 *       sen Nutzer gespeichert hat. Sind die beiden identisch, wird der Zugang freigegeben,
 *       ansonsten nicht.
 * 
 * 
 * Vorgehensweise:
 *
 * Precomputation-Phase: Wir berechnen Paare (Passwort, Message Digest) im Vor-
 * hinein und speichern diese in einer geeigneten Datenstruktur ab. Dabei dient der
 * Message Digest als Schluesselwert, mit dessen Hilfe das Passwort wiedergefunden
 * werden kann.
 * 
 * Online-Phase: Wenn wir einen Message Digest aufschnappen, schlagen wir in unse-
 * rer Sammlung nach, ob wir diesen Message Digest (zusammen mit einem passenden
 * Passwort) gespeichert haben. Falls ja, lesen wir das Passwort aus und benutzen es
 * fuer unseren Angriff.
 * 
 * 
 * Implementierungsidee:
 *
 * Angreifer:
 * Precomputation: 
 * Generiere zufaellige Kennwoerter und hashe sie
 * speichere diese in Hashtable mit 2^16 Eintraegen a 4 Byte (Kennwort) 
 * Speicherplatzbedarf: (Output max. 2^32 Mpeglichkeiten 
 *                      -> somit ist nach 2^16 Eintraegen eine Kollision wahrscheinlich)
 *
 *                      2^16 * 2^5 Bit = 2^21 Bit
 *                             2^18 Byte  
 *                      ca.    2 Megabyte
 * 
 * 
 * Opfer:
 * Terminal: 
 * Generiere zufaellige Kennwoerter und hashe diese
 * uebergebe Hash an Angreifer dieser vergleicht Hash mit denen in seiner Tabelle
 * bei Treffer ist das Spiel gewonnen
 * 
 * 
 *
 *
 *  Angreifer                                               Opfer
 * ######################################                   ############################################################
 * #                                     #                  #                                                          #
 * #    Tabelle:                         #                  #           Authentifizierung:          Server:            #
 * #    ------------------------------   #                  #           -------------------         -------            #
 * #    Eintrag      Hashwert Kennwort   #                  #           ####################        ##############     #
 * #    0            ######## ########   #                  #           # Benutzername     #        # vergleiche #     #
 * #    1            ######## ########   #                  #           ####################        # Hash       #     #
 * #    2            ######## ########   #                  #           # Kennwort         #        #            #     #
 * #    3            ######## ########   #                  #           ####################        # gewaehre   #     #
 * #    4            ######## ########   #                  #           # generierter Hash # -----> # Zugriff    #     #
 * #    5            ######## ########   #                  #           ####################   |    ##############     #
 * #    6            ######## ########   #                  #                                  |                       #
 * #    .                 .              #                  #                                  |                       #
 * #    .                 .              #                  #                                  |                       #
 * #    .                 .              #                  #                                  |                       #
 * #    .                 .              #                  #                                  |                       #
 * #    2^16 - 1     ######## ########   #                  ###################################|########################
 * #                                     #                                                     |
 * #                                     #                                                     |
 * #######################################                                                     |
 *                                                                                             |
 *                  ^                                                                          |
 *                  |                                                                          |
 *                  |                                                                          |
 *                  |--------------------------------------------------------------------------
 *
 * Angreifer:############################                   Terminal:#########################
 * ######################################                   ##################################
 * ######################################              |--->##Password########################
 * ######################################              |    #####|############################
 * ######################################              |    #####|SHA1########################
 * ######################################              |    #####|############################
 * ######################################              |    #####v############################
 * ########Precomputation################              |    ##Hash (4 Byte)###################
 * ###########Table######## #############              |         |              ^             
 * ########## Hash:Password #############----------------------->|              |True/        
 * ########## ############# #############              |         |              |False        
 * ########## ############# ##Lookup#####              |         v              |             
 * ########## ############# ###Found!####--------------|    Server:#############|#############
 * ########## ############# #############                   ####################|#############
 * ########## ############# ###NFound!###                   ##Lookup############|#############
 * ##########               ##Generate###                   #######Compare#-----|#############
 * ###########################bigger#####                   ##################################
 * ###########################table######                   ##################################
 * ######################################                   ##################################
 * ######################################                   ##################################
 * ######################################                   ##################################
 * ######################################                   ##################################
 *  
 *   Vergleiche erhaltenen Hash
 *   mit denen in der Tabelle
 *   Bei Treffer:
 *      sende Kennwort
 *   sonst:
 *      warte auf naechsten Hash
 *  
 *
 *
 * ToDo:
 * generateRandomPassword x
 * hash x
 * save x
 * compare x
 * send x
 *
 */

import java.util.Hashtable;
import java.util.Enumeration;
import java.io.*;
import java.net.*;
import java.lang.*;


public class Attacker {

    public static void main(String[] args) {

        HashPass ltuc = new HashPass();

        Hashtable<String, String> wordlist = new Hashtable<String, String>();

        int size = 0;

        if (args.length == 0) {
            size = 0xFFFFF;             // 2^20
        } else {
            try {
                size = new Integer(args[0]);
            } catch (Exception e) {
                size = 0xFFFFF;             // 2^20
            }
        }

        int i = 0;
        
        long startTime = System.currentTimeMillis();

        while (i < size) {
            ltuc = new HashPass();

            if (!wordlist.containsKey(ltuc.hash)) {
                wordlist.put(ltuc.getHash(), ltuc.getPassword());
                i++;
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Generating Hashtable took " + (endTime - startTime) + " milliseconds.");

        System.out.println("Size of Hashtable: " + wordlist.size());

/* 
   Print Table

        Enumeration enumValue = wordlist.elements();
        Enumeration enumKey = wordlist.keys();
        while ((enumValue.hasMoreElements() && enumKey.hasMoreElements())) {
            System.out.println("hashtable keys: " + enumKey.nextElement() + " " + "hashtable values: " + enumValue.nextElement());
        }
*/


/* Serialisierung
        startTime = System.currentTimeMillis();

        //Save File
        try {
            OutputStream outputStream = new FileOutputStream("table");

            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);

            objectOutput.writeObject(wordlist);

            objectOutput.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis();

        System.out.println("Saving Hashtable took " + (endTime - startTime) + " milliseconds.");


        //Read File
        Hashtable<String, String> dict = new Hashtable<String, String>();

        startTime = System.currentTimeMillis();


        try {
            FileInputStream inputStream = new FileInputStream("table");

            ObjectInputStream objectInput = new ObjectInputStream(inputStream);

            dict = (Hashtable) objectInput.readObject();

            objectInput.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        endTime = System.currentTimeMillis();

        System.out.println("Reading Hashtable took " + (endTime - startTime) + " milliseconds.");
*/

/* 
   Print Table

        Enumeration enumValue = dict.elements();
        Enumeration enumKey = dict.keys();
        while ((enumValue.hasMoreElements() && enumKey.hasMoreElements())) {
            System.out.println("hashtable keys: " + enumKey.nextElement() + " " + "hashtable values: " + enumValue.nextElement());
        }

        System.out.println("Size of Hashtable: " + wordlist.size());
*/

/* Connect to Terminal */
        String hostname = "localhost";
        int port = 1300;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String status;
        String capturedHash;

/* Debug 
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
*/
        try {
            clientSocket = new Socket(hostname, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for: " + hostname);
            System.exit(1);
        }

        i = 1;

        try {
            while ((capturedHash = in.readLine()) != null) {
                System.out.println("Captured Hash: " + capturedHash);
                if (wordlist.containsKey(capturedHash)) {
                    out.println(wordlist.get(capturedHash));
                    status = in.readLine();
                    System.out.println(status + " after " + i + " tries " + "Captured Hash: " + capturedHash + " maps to following Password: " + wordlist.get(capturedHash));
                } else {
                    System.out.println(capturedHash + " Not in Table");
                    out.println("Next");
                    status = in.readLine();
                    //status = in.readLine();
                    //System.out.println(status);
                }
                i++;
            }
/* cleanup */
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.exit(1);
        }
    }
}
