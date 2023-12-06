package com.auth.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Verifications {
    public static String otpVerification(Integer otp) {
        String htmlContent = readHTMLFromFile("src/main/resources/templates/otp-verification.html");
        htmlContent = htmlContent.replace("${otp}", String.valueOf(otp));
        return htmlContent;
    }
    public static String resetPasswordVerification(String url) {
        String htmlContent = readHTMLFromFile("src/main/resources/templates/reset-verification.html");
        htmlContent = htmlContent.replace("${url}", url);
        return htmlContent;
    }
    private static String readHTMLFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
