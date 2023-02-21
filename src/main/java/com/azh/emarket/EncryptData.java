package com.azh.emarket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptData {
    public static String generate(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(str.getBytes());
            return bytesToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String bytesToHex(byte[] digest) {
        StringBuffer hexString = new StringBuffer();

        for (int i = 0;i<digest.length;i++) {
            String hex = Integer.toHexString(0xFF & digest[i]); // bytes widen to int, need mask, prevent sign extension
            // get last 8 bits
            if (hex.length() % 2 == 1) { // if half hex, pad with zero, e.g \t
                hex = "0" + hex;
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
