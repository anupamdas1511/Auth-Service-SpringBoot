package com.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
public class Otp {
    @Id
    private String email;
    private Integer otp;
    private LocalDateTime createdAt;

    public Otp(String email, Integer otp) {
        this.email = email;
        this.otp = otp;
        this.createdAt = LocalDateTime.now();
    }

    public Otp() {
        super();
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
