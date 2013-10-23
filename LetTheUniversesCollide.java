/**
 * File Name     : LetTheUniversesCollide.java
 * Purpose       :
 * Creation Date : 23-10-2013
 * Last Modified : Wed 23 Oct 2013 02:15:36 PM CEST
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class LetTheUniversesCollide {
	public static void main( String[] args ) {
		System.out.println("Hello World!");	
	}
}
