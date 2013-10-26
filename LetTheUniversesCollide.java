/**
 * File Name     : LetTheUniversesCollide.java
 * Purpose       :
 * Creation Date : 23-10-2013
 * Last Modified : Thu 24 Oct 2013 01:48:00 PM CEST
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
 * 						-> somit ist nach 2^16 Eintraegen eine Kollision wahrscheinlich)
 *
 * 						2^16 * 2^5 Bit = 2^21 Bit
 * 							   2^18 Byte  
 *						ca.    2 Megabyte
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
 *	Angreifer												Opfer
 * ######################################	               	############################################################
 * #									 #   				#                                                          #
 * #    Tabelle:						 #   				#			Authentifizierung:			Server:            #
 * #	------------------------------	 #   				#			-------------------			-------            #
 * #	Eintrag		 Hashwert Kennwort	 #   				#			####################        ##############     #
 * #	0			 ######## ########	 #   				#			# Benutzername     #        # vergleiche #     #
 * #	1			 ######## ########	 #   				#			####################        # Hash       #     #
 * #	2			 ######## ########	 #   				#			# Kennwort         #        #            #     #
 * #	3			 ######## ########	 #   				#			####################        # gewaehre   #     #
 * #	4			 ######## ########	 #   				#			# generierter Hash # -----> # Zugriff    #     #
 * #	5			 ######## ########	 #   				#			####################   |    ##############     #
 * #	6			 ######## ########	 #   				#			                       |                       #
 * #	.				  .				 #   				#                                  |                       #
 * #	.				  .				 #   				#                                  |                       #
 * #	.				  .				 #   				#                                  |                       #
 * #	.				  .				 #   				#                                  |                       #
 * #	2^16 - 1	 ######## ########	 #   				###################################|########################
 * #									 #													   |
 * #									 #													   |
 * #######################################													   |
 * 																							   |
 * 					^																		   |
 * 					|																		   |
 * 					|																		   |
 * 					|--------------------------------------------------------------------------
 *  
 *   Vergleiche erhaltenen Hash
 *   mit denen in der Tabelle
 *   Bei Treffer:
 *   	sende Kennwort
 *   sonst:
 *   	warte auf naechsten Hash
 *  
 *
 *
 * ToDo:
 * generateRandomPassword
 * hash
 * save
 * compare
 * send
 *
 */

import java.util.Hashtable;
import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class LetTheUniversesCollide {
	

	String password;
	String hash;

  	LetTheUniversesCollide () {
	  // http://en.wikipedia.org/wiki/Universally_unique_identifier#Random_UUID_probability_of_duplicates 
	  password = UUID.randomUUID().toString();
	  password = password.substring(28,password.length());      //8byte Kennwort
	  try {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(password.getBytes());
		byte[] byteDigest = md.digest();
		hash = bytesToHex(byteDigest);
	  } catch (NoSuchAlgorithmException e) {
		System.out.println("Exception: " + e);
	  }
	  
	}

	void truncate() {
	  hash = hash.substring(32,hash.length());

	}


	public static String bytesToHex(byte[] b) {
	  char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
				  '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	  StringBuffer buf = new StringBuffer();
	  for (int j=0; j<b.length; j++) {
		buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
		buf.append(hexDigit[b[j] & 0x0f]);
	  }
	  return buf.toString();
	}

	public static void main( String[] args ) {

   		LetTheUniversesCollide ltuc = new LetTheUniversesCollide();

		//System.out.println("password " + ltuc.password + " - " + "hash: " + ltuc.hash);	
		ltuc.truncate();
		//System.out.println("password " + ltuc.password + " - " + "hash: " + ltuc.hash);	

		Hashtable<String, String> wordlist = new Hashtable<String, String>();

		int i = 0;
        
        while (i < 0xFFFF) {

   		  ltuc = new LetTheUniversesCollide();
          ltuc.truncate();

          //System.out.println("Size of Hashtable: " + wordlist.size());
          //if (!wordlist.containsKey(ltuc.hash)) {
          wordlist.put(ltuc.hash, ltuc.password);
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

        findKey = ltuc.hash;

        if (wordlist.containsKey(findKey)) {
          System.out.println("Found in Table: hash: " + findKey + " maps to password: " + wordlist.get(findKey));
        } else {
          System.out.println("Not found. Let us guess till we find it ...");
          while (i < 0xFFFF) {

            ltuc = new LetTheUniversesCollide();
            ltuc.truncate();
            if (ltuc.hash == findKey) {
              System.out.println("hash: " + findKey + " " + ltuc.hash + " maps to password: " + ltuc.password);
              i = 0xFFFF;
            } else {
            i++;
            }
          }
        }

        System.out.println("Size of Hashtable: " + wordlist.size());

	}
}
