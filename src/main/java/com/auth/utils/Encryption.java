package com.auth.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Encryption {
    public static String encode(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(message.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hash = new StringBuilder(no.toString(16));
            while (hash.length() < 32) {
                hash.insert(0, "0");
            }
            return hash.toString();

        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static boolean match(String message, String hashedMessage) {
        return Objects.equals(encode(message), hashedMessage);
    }
}
