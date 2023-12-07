package com.auth.services.impl;

import com.auth.entities.Otp;
import com.auth.entities.ResetPassword;
import com.auth.entities.User;
import com.auth.exceptions.Exception;
import com.auth.repositories.OtpRepository;
import com.auth.repositories.ResetPasswordRepository;
import com.auth.repositories.UserRepository;
import com.auth.services.AuthService;
import com.auth.services.NotificationService;
import com.auth.utils.Encryption;
import com.auth.utils.RandomGenerator;
import com.auth.utils.Validators;
import io.jsonwebtoken.Jwts;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.auth.exceptions.ExceptionCodes.*;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Override
    public void signup(User user) throws Exception, MessagingException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        boolean isValidEmail = Validators.isValidEmail(email);

        if (firstName == null) {
            throw new Exception("First name is required", BAD_INPUT.getCode());
        }
        if (lastName == null) {
            throw new Exception("Last name is required", BAD_INPUT.getCode());
        }
        if (email == null) {
            throw new Exception("Email is required", BAD_INPUT.getCode());
        }
        if (password == null) {
            throw new Exception("Password is required", BAD_INPUT.getCode());
        }
        if (password.length() < 6) {
            throw new Exception("Password must be at least 6 characters", BAD_INPUT.getCode());
        }
        if (!isValidEmail) {
            throw new Exception("Invalid email", UNAUTHORIZED.getCode());
        }
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null && !existingUser.getEmailVerified()) {
            generateAndSendOtp(email);
            throw new Exception("User already exists. OTP has been sent to your mail.", CONFLICT.getCode());
        } else if (existingUser != null) {
            throw new Exception("User already exists.", CONFLICT.getCode());
        }

        String hashedPassword = Encryption.encode(password);

        User newUser = new User(email, firstName, lastName, hashedPassword, false);
        userRepository.save(newUser);
        generateAndSendOtp(email);
    }

    @Override
    public void login(User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        boolean isValidEmail = Validators.isValidEmail(email);
        Boolean isGoogleSignIn = user.getGoogleSignIn();

        if (email == null) {
            throw new Exception("Email is required", BAD_INPUT.getCode());
        }
        if (password == null && isGoogleSignIn != null && !isGoogleSignIn) {
            throw new Exception("Password is required", BAD_INPUT.getCode());
        }
        if (!isValidEmail) {
            throw new Exception("Invalid email", UNAUTHORIZED.getCode());
        }
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null && isGoogleSignIn != null && isGoogleSignIn) {
            existingUser = new User();
            existingUser.setEmail(email);
            existingUser.setFirstname(firstName);
            existingUser.setLastname(lastName);
            existingUser.setEmailVerified(true);
            userRepository.save(existingUser);
        } else if (existingUser == null) {
            throw new Exception("User not found", NOT_FOUND.getCode());
        }

        if (isGoogleSignIn != null && !isGoogleSignIn) {
            if (!Encryption.match(password, existingUser.getPassword())) {
                throw new Exception("Invalid password", UNAUTHORIZED.getCode());
            }
            if (!existingUser.getEmailVerified()) {
                throw new Exception("Email not verified", UNAUTHORIZED.getCode());
            }
        }

//        Jwts.builder()
//                .claim("email", existingUser.getEmail())
//                .claim("isEmailVerified", existingUser.getEmailVerified())
//                .compact();
    }

    @Override
    public void generateAndSendOtp(String email) throws MessagingException {
        if (email == null) {
            throw new Exception("Email is required", BAD_INPUT.getCode());
        }
        if (!Validators.isValidEmail(email)) {
            throw new Exception("Invalid email", BAD_INPUT.getCode());
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found", NOT_FOUND.getCode());
        }
        if (user.getEmailVerified()) {
            throw new Exception("Email is already verified", FORBIDDEN.getCode());
        }

        Integer otpToken;
        Otp otp = otpRepository.findByEmail(email);
        if (otp != null) {
            otpToken = otp.getOtp();
        } else {
            otpToken = Integer.parseInt(RandomGenerator.generate(6));
            Otp newOtp = new Otp(email, otpToken);
            otpRepository.save(newOtp);
        }

        NotificationService.sendOtp(email, otpToken);
    }

    @Override
    public void validateOtp(String email, Integer otp) throws MessagingException {
        if (email == null) {
            throw new Exception("Email is required", BAD_INPUT.getCode());
        }
        if (!Validators.isValidEmail(email)) {
            throw new Exception("Invalid email", BAD_INPUT.getCode());
        }
        if (otp == null) {
            throw new Exception("OTP is required", BAD_INPUT.getCode());
        }
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new Exception("User not found", NOT_FOUND.getCode());
        }
        if (existingUser.getEmailVerified()) {
            throw new Exception("User already verified", CONFLICT.getCode());
        }
        Otp otpToken = otpRepository.findByEmail(email);
        if (otpToken == null) {
            generateAndSendOtp(email);
            throw new Exception("Otp expired... New otp sent", NOT_FOUND.getCode());
        }
        if (!Objects.equals(otpToken.getOtp(), otp)) {
            throw new Exception("Invalid otp", UNAUTHORIZED.getCode());
        }
        otpRepository.deleteById(email);

        existingUser.setEmailVerified(true);
        userRepository.save(existingUser);
//        Jwts.builder()
//                .claim("email", existingUser.getEmail())
//                .claim("isEmailVerified", existingUser.getEmailVerified())
//                .compact();
    }

    @Override
    public User getUser(String email) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new Exception("User not found", NOT_FOUND.getCode());
        }
        return existingUser;
    }

    @Override
    public void forgotPassword(String email) throws MessagingException {
        if (email == null) {
            throw new Exception("Email is required", BAD_INPUT.getCode());
        }
        if (!Validators.isValidEmail(email)) {
            throw new Exception("Invalid email", BAD_INPUT.getCode());
        }
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new Exception("User not found", NOT_FOUND.getCode());
        }
        ResetPassword resetPassword = resetPasswordRepository.findByEmail(email);
        String url = System.getenv("FORGOT_PASS_FRONTEND_URL");
        String randomCode;
        if (resetPassword == null) {
            randomCode = RandomGenerator.generate(50, true);
            resetPassword = new ResetPassword(email, randomCode);
            resetPasswordRepository.save(resetPassword);
        } else {
            randomCode = resetPassword.getCode();
        }
        String token = Encryption.encode(email + randomCode);
        url += "?token=" + token;

        NotificationService.sendResetLink(email, url);
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        if (email == null) {
            throw new Exception("Email is required", BAD_INPUT.getCode());
        }
        if (!Validators.isValidEmail(email)) {
            throw new Exception("Invalid email", BAD_INPUT.getCode());
        }
        if (oldPassword == null) {
            throw new Exception("Old password is required", BAD_INPUT.getCode());
        }
        if (newPassword == null) {
            throw new Exception("New password is required", BAD_INPUT.getCode());
        }
        if (newPassword.length() < 6) {
            throw new Exception("Password must be at least 6 characters", BAD_INPUT.getCode());
        }
        if (oldPassword.equals(newPassword)) {
            throw new Exception("New password cannot be same as old password", BAD_INPUT.getCode());
        }
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) {
            throw new Exception("User not found", NOT_FOUND.getCode());
        }
        boolean isOldPasswordValid = Encryption.match(oldPassword, existingUser.getPassword());
        if (!isOldPasswordValid) {
            throw new Exception("Old password did not match", UNAUTHORIZED.getCode());
        }
        String newHashedPassword = Encryption.encode(newPassword);
        existingUser.setPassword(newHashedPassword);
        userRepository.save(existingUser);
    }
}
