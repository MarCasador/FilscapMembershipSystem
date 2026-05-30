package com.example.membershipsystem.repository;

import com.example.membershipsystem.model.MemberApprovalLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberApprovalLogRepository extends JpaRepository<MemberApprovalLog, Long> {

    List<MemberApprovalLog> findByMemberIdOrderByCreatedAtDesc(Long memberId);
    List<MemberApprovalLog> findAllByOrderByCreatedAtDesc();
}