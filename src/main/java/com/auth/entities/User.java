package com.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class User {
    @Id
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Boolean isEmailVerified = false;
    private Boolean isGoogleSignIn;

    public User(String email, String firstname, String lastname, String password, Boolean isGoogleSignIn) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.isGoogleSignIn = isGoogleSignIn;
    }

    public User() {
        super();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public Boolean getGoogleSignIn() {
        return isGoogleSignIn;
    }

    public void setGoogleSignIn(Boolean googleSignIn) {
        isGoogleSignIn = googleSignIn;
    }
}
