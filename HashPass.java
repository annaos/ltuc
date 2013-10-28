/**
 * File Name     : HashPass.java
 * Purpose       :
 * Creation Date : 27-10-2013
 * Last Modified : Mon 28 Oct 2013 08:43:29 PM CET
 * Created By    :
 *
 */


import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashPass {

    String password;
    String hash;
/*
 * Generiere Hash aus zufaelligem Kennwort
 * schneide Hash ab (die letzten 8 Zeichen)
 */
    HashPass() {
        password = UUID.randomUUID().toString();
        password = password.substring(20,password.length());      // 8byte Kennwort
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());

            byte[] byteDigest = md.digest();
            hash = bytesToHex(byteDigest);
            truncate();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception: " + e);
        }
    }
/*
 * Generiere Hash aus gegebenen Kennwort
 *
 */
    HashPass(String s) {
        password = s;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());

            byte[] byteDigest = md.digest();
            hash = bytesToHex(byteDigest);
            truncate();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception: " + e);
        }
    }
/*
 * Kennwort sowie Hashwert zurueckgeben
 *
 */
    String getPassword() {
        return password;
    }

    String getHash() {
        return hash;
    }
/*
 * Hashwert auf die letzten 8 Zeichen kuerzen
 *
 */
    void truncate() {
        hash = hash.substring(32,hash.length());
    }
/*
 * Bytearray in Hexstring umwandeln
 *
 */
    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                   '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buffer = new StringBuffer();

        for (int j = 0; j < b.length; j++) {
            buffer.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buffer.append(hexDigit[b[j] & 0x0f]);
        }
        return buffer.toString();
    }
}
