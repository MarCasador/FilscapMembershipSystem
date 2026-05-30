package com.example.membershipsystem.controller;

import com.example.membershipsystem.model.Account;
import com.example.membershipsystem.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accountdetails")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    /* =========================
       GET ALL ACCOUNTS
    ========================= */
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /* =========================
       GET ACCOUNT BY ID
    ========================= */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {

        Account account = accountRepository.findById(id)
                .orElse(null);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(account);
    }

    /* =========================
       UPDATE ACCOUNT
    ========================= */
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(
            @PathVariable Long id,
            @RequestBody Account updated) {

        Account account = accountRepository.findById(id)
                .orElse(null);

        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        account.setUsername(updated.getUsername());
        account.setEmail(updated.getEmail());

        // ito depende sa field name mo sa Account entity
        account.setAccessLevel(updated.getAccessLevel());

        Account saved = accountRepository.save(account);

        return ResponseEntity.ok(saved);
    }

    /* =========================
       DELETE ACCOUNT
    ========================= */
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
    }
}