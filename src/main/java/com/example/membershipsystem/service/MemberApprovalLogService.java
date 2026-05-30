package com.example.membershipsystem.service;

import com.example.membershipsystem.model.MemberApprovalLog;
import com.example.membershipsystem.model.Account;
import com.example.membershipsystem.repository.MemberApprovalLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberApprovalLogService {

    private final MemberApprovalLogRepository repo;

    public MemberApprovalLogService(MemberApprovalLogRepository repo) {
        this.repo = repo;
    }

    public void log(Long memberId, String action, Account admin) {

        MemberApprovalLog log = new MemberApprovalLog();

        log.setMemberId(memberId);
        log.setAction(action);

        log.setAdminId(admin.getId());
        log.setAdminEmail(admin.getEmail());
        log.setAdminRole(admin.getAccessLevel());

        repo.save(log);
    }

    public List<MemberApprovalLog> getLogs(Long memberId) {
        return repo.findByMemberIdOrderByCreatedAtDesc(memberId);
    }

    // ✅ FIXED
    public List<MemberApprovalLog> getAllLogs() {
        return repo.findAllByOrderByCreatedAtDesc();
    }
}