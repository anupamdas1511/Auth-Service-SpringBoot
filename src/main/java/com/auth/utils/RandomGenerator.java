package com.auth.utils;

import java.security.SecureRandom;

public class RandomGenerator {
    private static final String NUMS = "0123456789";
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static char getRandomCharacter(boolean useCharacter) {
        String validChars = NUMS;
        if (useCharacter) {
            validChars += CHARS;
        }
        SecureRandom random = new SecureRandom();
        return validChars.charAt(random.nextInt(validChars.length()));
    }

    public static String generate(int len, boolean ...args) {
        boolean useChar = (args.length == 1);
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < len; i++) {
            otp.append(getRandomCharacter(useChar));
        }
        return otp.toString();
    }
}
