package com.example.membershipsystem.controller;

import com.example.membershipsystem.model.EmergencyContact;
import com.example.membershipsystem.model.Member;
import com.example.membershipsystem.repository.EmergencyContactRepository;
import com.example.membershipsystem.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emergency-contact")
@CrossOrigin(origins = "*")
public class EmergencyContactController {

    private final MemberRepository memberRepository;
    private final EmergencyContactRepository emergencyContactRepository;

    public EmergencyContactController(
            MemberRepository memberRepository,
            EmergencyContactRepository emergencyContactRepository
    ) {
        this.memberRepository = memberRepository;
        this.emergencyContactRepository = emergencyContactRepository;
    }

    // ================= SAVE =================
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Map<String, Object> body) {

        try {

            Long memberId = Long.parseLong(body.get("memberId").toString());

            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            EmergencyContact ec = new EmergencyContact();
            ec.setMember(member);
            ec.setName((String) body.getOrDefault("name", ""));
            ec.setRelation((String) body.getOrDefault("relation", ""));
            ec.setEmergencyContactNo((String) body.getOrDefault("emergencyContactNo", ""));
            ec.setEmergencyEmailAddress((String) body.getOrDefault("emergencyEmailAddress", ""));
            ec.setEmergencyAddress((String) body.getOrDefault("emergencyAddress", ""));

            emergencyContactRepository.save(ec);

            return ResponseEntity.ok("saved");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error: " + e.getMessage());
        }
    }

    // ================= GET CONTACTS BY MEMBER =================
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<EmergencyContact>> getByMember(
            @PathVariable Long memberId
    ) {

        List<EmergencyContact> contacts =
                emergencyContactRepository.findByMemberId(memberId);

        return ResponseEntity.ok(contacts);
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!emergencyContactRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        emergencyContactRepository.deleteById(id);

        return ResponseEntity.ok("Deleted successfully");
    }
}