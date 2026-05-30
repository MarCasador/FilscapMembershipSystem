package com.example.membershipsystem.controller;

import com.example.membershipsystem.model.Account;
import com.example.membershipsystem.model.Member;
import com.example.membershipsystem.model.MemberApprovalLog;
import com.example.membershipsystem.service.MemberApprovalLogService;
import com.example.membershipsystem.service.MemberService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@CrossOrigin
public class AdminController {

    private final MemberService memberService;
    private final MemberApprovalLogService memberApprovalLogService;

    @Autowired
    private HttpSession session;

    public AdminController(MemberService memberService,
                           MemberApprovalLogService memberApprovalLogService) {
        this.memberService = memberService;
        this.memberApprovalLogService = memberApprovalLogService;
    }

    /* =========================
       GET ALL MEMBERS
    ========================= */
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    /* =========================
       GET MEMBER BY ID
    ========================= */
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /* =========================
       UPDATE MEMBER INFO
    ========================= */
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable Long id,
            @RequestBody Member updated
    ) {
        return ResponseEntity.ok(memberService.updateMember(id, updated));
    }

    /* =========================
       UPDATE STATUS + AUDIT LOG
    ========================= */
    @PutMapping("/{id}/status")
    public ResponseEntity<Member> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {

        String status = body.get("status");

        Member updated = memberService.updateStatus(id, status);

        Account admin = getCurrentAdmin();

        memberApprovalLogService.log(
                id,
                status != null ? status.toUpperCase() : "UNKNOWN",
                admin
        );

        return ResponseEntity.ok(updated);
    }

    /* =========================
       REPORT FILTER
    ========================= */
    @GetMapping("/report/members")
    public List<Member> getFilteredReport(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String quarter
    ) {

        LocalDate parsedDate = null;

        if (date != null && !date.isEmpty()) {
            parsedDate = LocalDate.parse(date);
        }

        return memberService.getFilteredReport(
                status,
                category,
                parsedDate,
                month,
                quarter
        );
    }

    /* =========================
       ADMIN FROM SESSION (NO SPRING SECURITY)
    ========================= */
    private Account getCurrentAdmin() {

        Account admin = (Account) session.getAttribute("admin");

        if (admin == null) {
            Account system = new Account();
            system.setEmail("SYSTEM");
            system.setAccessLevel("SYSTEM");
            return system;
        }

        return admin;
    }

    /* =========================
       AUDIT LOGS (MEMBER SPECIFIC)
    ========================= */
    @GetMapping("/{id}/logs")
    public List<MemberApprovalLog> getLogs(@PathVariable Long id) {
        return memberApprovalLogService.getLogs(id);
    }

    /* =========================
       ALL SYSTEM LOGS
    ========================= */
    @GetMapping("/logs")
    public List<MemberApprovalLog> getAllLogs() {
        return memberApprovalLogService.getAllLogs();
    }


}
