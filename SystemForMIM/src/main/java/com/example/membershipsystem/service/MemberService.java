package com.example.membershipsystem.service;

import com.example.membershipsystem.model.Member;
import com.example.membershipsystem.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* =========================
       GET ALL
    ========================= */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /* =========================
       GET BY ID
    ========================= */
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    /* =========================
       UPDATE MEMBER INFO
    ========================= */
    public Member updateMember(Long id, Member updated) {

        Member member = getMemberById(id);

        member.setFirstName(updated.getFirstName());
        member.setMiddleName(updated.getMiddleName());
        member.setLastName(updated.getLastName());
        member.setEmailAddress(updated.getEmailAddress());
        member.setBirthdate(updated.getBirthdate());
        member.setAge(updated.getAge());
        member.setNationality(updated.getNationality());
        member.setCurrentAddress(updated.getCurrentAddress());
        member.setCellNumber(updated.getCellNumber());
        member.setBandName(updated.getBandName());
        member.setStatus(updated.getStatus());

        return memberRepository.save(member);
    }

    /* =========================
       UPDATE STATUS ONLY
    ========================= */
    public Member updateStatus(Long id, String status) {

        Member member = getMemberById(id);
        member.setStatus(status);

        return memberRepository.save(member);
    }

    /* =========================
       REPORT FILTER (FIXED SIGNATURE)
    ========================= */
    public List<Member> getFilteredReport(
            String status,
            Long category,
            LocalDate date,
            String month,
            String quarter
    ) {

        return memberRepository.getFilteredReport(
                status,
                category,
                date,
                month,
                quarter
        );
    }
}