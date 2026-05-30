package com.example.membershipsystem.controller;

import com.example.membershipsystem.model.Member;
import com.example.membershipsystem.model.MembersCategory;
import com.example.membershipsystem.repository.MemberRepository;
import com.example.membershipsystem.repository.MembersCategoryRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MembersCategoryRepository categoryRepository;

    public MemberController(
            MemberRepository memberRepository,
            MembersCategoryRepository categoryRepository
    ) {
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/userDashboard")
    public String dashboard() {
        return "userDashboard";
    }

    @PostMapping("/save")
    @ResponseBody
    public String saveMember(Member formMember,
                             @RequestParam Long categoryId,
                             HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "ERROR: Not logged in";
        }

        MembersCategory category =
                categoryRepository.findById(categoryId).orElse(null);

        if (category == null) {
            return "ERROR: Invalid category";
        }

        Member member = memberRepository.findByUserId(userId);

        // NEW MEMBER
        if (member == null) {

            formMember.setUserId(userId);
            formMember.setCategory(category);

            Member saved = memberRepository.save(formMember);

            return String.valueOf(saved.getId());
        }

        // UPDATE EXISTING MEMBER
        member.setFirstName(formMember.getFirstName());
        member.setLastName(formMember.getLastName());
        member.setMiddleName(formMember.getMiddleName());
        member.setBirthdate(formMember.getBirthdate());
        member.setAge(formMember.getAge());
        member.setBirthplace(formMember.getBirthplace());
        member.setNationality(formMember.getNationality());
        member.setCompanyName(formMember.getCompanyName());
        member.setCivilStatus(formMember.getCivilStatus());
        member.setBandName(formMember.getBandName());
        member.setCellNumber(formMember.getCellNumber());
        member.setHomeNumber(formMember.getHomeNumber());
        member.setEmailAddress(formMember.getEmailAddress());
        member.setPseudonym(formMember.getPseudonym());
        member.setCurrentAddress(formMember.getCurrentAddress());
        member.setPermanentAddress(formMember.getPermanentAddress());

        member.setCategory(category);

        Member saved = memberRepository.save(member);

        return String.valueOf(saved.getId());
    }

    @GetMapping("/current")
    @ResponseBody
    public Member getCurrentMember(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return null;
        }

        return memberRepository.findByUserId(userId);
    }
    @PutMapping("/{id}")
    @ResponseBody
    public Member updateMember(
            @PathVariable Long id,
            @RequestBody Member updatedMember
    ) {

        Member member = memberRepository
                .findById(id)
                .orElse(null);

        if (member == null) {
            return null;
        }

        // UPDATE ONLY THE FIELDS YOU WANT
        member.setEmailAddress(updatedMember.getEmailAddress());
        member.setBirthdate(updatedMember.getBirthdate());
        member.setAge(updatedMember.getAge());
        member.setBandName(updatedMember.getBandName());

        // OPTIONAL
        member.setFirstName(updatedMember.getFirstName());
        member.setLastName(updatedMember.getLastName());

        return memberRepository.save(member);
    }
}