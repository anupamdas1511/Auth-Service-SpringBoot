package com.auth.services;

import com.auth.utils.Verifications;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

public class NotificationService {

//    @Value("${SENDER_EMAIL}")
    private static String SENDER_EMAIL = "techwithanupam1511@gmail.com";
//    @Value("${MAIL_HOST}")
    private static String host = "smtp.gmail.com";
    private static Session session;
    public static void sendOtp(String email, Integer otp) throws MessagingException {
        setProperties();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_EMAIL));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Otp Verification");
        message.setContent(Verifications.otpVerification(otp), "text/html");
        Transport.send(message);
    }
    public static void sendResetLink(String email, String url) throws MessagingException {
        setProperties();
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER_EMAIL));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("Reset Password Verification");
        message.setContent(Verifications.resetPasswordVerification(url), "text/html");
        Transport.send(message);
    }
    private static void setProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("techwithanupam1511@gmail.com", "odvefbybrkljetqc");
            }
        });
    }
}
