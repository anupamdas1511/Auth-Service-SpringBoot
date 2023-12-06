package com.auth.repositories;

import com.auth.entities.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, String> {
    ResetPassword findByEmail(String email);
}
