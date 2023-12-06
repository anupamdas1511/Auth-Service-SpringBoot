package com.auth.services;

import com.auth.entities.User;
import jakarta.mail.MessagingException;

public interface AuthService {
    void signup(User user) throws Exception;
    void login(User user) throws Exception;
    void generateAndSendOtp(String email) throws MessagingException;
    void validateOtp(String email, Integer otp) throws MessagingException;
    User getUser(String email);
    void forgotPassword(String email) throws MessagingException;
    void changePassword(String email, String oldPassword, String newPassword);
}