package com.example.membershipsystem.repository;

import com.example.membershipsystem.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsernameAndPassword(String username, String password);
}