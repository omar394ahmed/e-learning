package com.example.demo.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByToken(String token);
     Optional<User> findByUsername(String username);
     Optional<User> findByIdAndConfirmationCode(Long id , int code);
    Optional<User> findByUsernameAndConfirmationCode(String email , int code);

    Optional<User> findByUsernameAndPassword(String username , String password);



    }
