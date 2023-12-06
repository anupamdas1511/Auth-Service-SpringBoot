package com.auth.controllers;

import com.auth.entities.User;
import com.auth.services.AuthService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    // signup user
    @PostMapping("/signup")
    public void signup(@RequestBody User user) throws Exception {
        authService.signup(user);
    }
    @PostMapping("/login")
    public void login(@RequestBody User user) throws Exception {
        authService.login(user);
    }
    @PostMapping("/generate-otp")
    public void generateOtp(@RequestBody String email) throws Exception {
        authService.generateAndSendOtp(email);
    }
    @PostMapping("/validate-otp")
    public void validateOtp(@RequestParam String email, @RequestParam Integer otp) throws Exception {
        authService.validateOtp(email, otp);
    }
    @GetMapping("/user")
    public User getUser(@RequestParam String email) {
        return authService.getUser(email);
    }
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody String email) throws MessagingException {
        authService.forgotPassword(email);
    }
    @PostMapping("/change-password")
    public void changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) throws Exception {
        authService.changePassword(email, oldPassword, newPassword);
    }
}
