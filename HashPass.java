/**
 * File Name     : HashPass.java
 * Purpose       :
 * Creation Date : 27-10-2013
 * Last Modified : Mon 28 Oct 2013 05:50:48 PM CET
 * Created By    :
 *
 */


import java.util.UUID;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashPass {

    String password;
    String hash;

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


    String getPassword() {
        return password;
    }

    String getHash() {
        return hash;
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
}
