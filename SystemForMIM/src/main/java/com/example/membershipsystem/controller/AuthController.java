package com.example.membershipsystem.controller;

import com.example.membershipsystem.model.Account;
import com.example.membershipsystem.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AccountRepository accountRepo;

    public AuthController(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    /* LOGIN PAGE */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /* REGISTER PAGE */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /* REGISTER PROCESS */
    @PostMapping("/register")
    public String register(@ModelAttribute Account account) {
        account.setAccessLevel("USER");
        accountRepo.save(account);

        return "redirect:/login";
    }

    /* LOGIN PROCESS (ONLY ONE) */
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {

        Account acc = accountRepo.findByUsernameAndPassword(username, password)
                .orElse(null);

        if (acc == null) {
            return "redirect:/login?error=true";
        }

        // 🔴 ADD THIS BLOCK
        if ("DEACTIVATE".equalsIgnoreCase(acc.getAccessLevel())) {
            return "redirect:/login?error=deactivated";
        }

        session.setAttribute("userId", acc.getId());
        session.setAttribute("accessLevel", acc.getAccessLevel());
        session.setAttribute("admin", acc);

        if ("ADMIN".equalsIgnoreCase(acc.getAccessLevel())) {
            return "redirect:/adminDashboard";
        }

        return "redirect:/userDashboard";
    }

    /* USER DASHBOARD */
    @GetMapping("/userDashboard")
    public String userDashboard(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        return "userDashboard";
    }

    /* ADMIN DASHBOARD */
    @GetMapping("/adminDashboard")
    public String adminDashboard(HttpSession session) {
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        return "adminDashboard";
    }

    /* LOGOUT */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}