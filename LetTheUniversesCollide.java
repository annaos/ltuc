/**
 * File Name     : LetTheUniversesCollide.java
 * Purpose       :
 * Creation Date : 23-10-2013
 * Last Modified : Sun 27 Oct 2013 11:14:51 PM CET
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


public class LetTheUniversesCollide {

    public static void main( String[] args ) throws IOException {

        HashPass ltuc = new HashPass();

        //System.out.println("password " + ltuc.password + " - " + "hash: " + ltuc.hash);   
        //System.out.println("password " + ltuc.password + " - " + "hash: " + ltuc.hash);   

        Hashtable<String, String> wordlist = new Hashtable<String, String>();

        int i = 0;
        
        while (i < 0xFFFF) {
            ltuc = new HashPass();

            //System.out.println("Size of Hashtable: " + wordlist.size());
            //if (!wordlist.containsKey(ltuc.hash)) {
            wordlist.put(ltuc.getHash(), ltuc.getPassword());
            i++;
            //System.out.println("Size of Hashtable: " + wordlist.size());
            //}
        }

        Enumeration enumValue = wordlist.elements();
        Enumeration enumKey = wordlist.keys();

        while ((enumValue.hasMoreElements() && enumKey.hasMoreElements())) {
            System.out.println("hashtable keys: " + enumKey.nextElement() + " " + "hashtable values: " + enumValue.nextElement());
        }

        //System.out.println("Size of Hashtable: " + wordlist.size());

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

        Hashtable<String, String> dict = new Hashtable<String, String>();

        //Read File
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

        enumValue = dict.elements();
        enumKey = dict.keys();
        while ((enumValue.hasMoreElements() && enumKey.hasMoreElements())) {
            System.out.println("hashtable keys: " + enumKey.nextElement() + " " + "hashtable values: " + enumValue.nextElement());
        }

        //find a Hash
        String findKey = new String();

        findKey = ltuc.getHash();

        if (wordlist.containsKey(findKey)) {
            System.out.println("Found in Table: hash: " + findKey + " maps to password: " + wordlist.get(findKey));
        } else {
            System.out.println("Not found. Let us guess till we find it ...");

            while (i < 0xFFFF) {
                ltuc = new HashPass();
                if (ltuc.getHash() == findKey) {
                    System.out.println("hash: " + findKey + " " + ltuc.getHash() + " maps to password: " + ltuc.getPassword());
                    i = 0xFFFF;
                } else {
                    i++;
                }
            }
        }

        System.out.println("Size of Hashtable: " + wordlist.size());

        /*
        String hostname = "localhost";
        int port = 1300;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            clientSocket = new Socket(hostname, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for: " + hostname);
            System.exit(1);
        }

        String capturedHash;

        i = 1;

        while ((capturedHash = in.readLine()) != null) {
            System.out.println("Captured Hash: " + capturedHash);
            if (wordlist.containsKey(capturedHash)) {
                out.println(wordlist.get(capturedHash));
                System.out.println(i + " tries " + "Captured Hash: " + capturedHash + " maps to following Password: " + wordlist.get(capturedHash));
            } else {
                System.out.println(capturedHash + " Not in Table");
            }
            i++;
        }

        out.close();
        in.close();
        clientSocket.close();
        */
    }
}
