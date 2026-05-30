package com.example.membershipsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "contact")
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ContactID")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Relation")
    private String relation;

    @Column(name = "emergency_address")
    private String emergencyAddress;

    @Column(name = "emergency_email_address")
    private String emergencyEmailAddress;

    @Column(name = "emergency_contact_no")
    private String emergencyContactNo;

    // RELATION TO MEMBER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler",
            "emergencyContacts"
    })
    private Member member;

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getEmergencyAddress() {
        return emergencyAddress;
    }

    public void setEmergencyAddress(String emergencyAddress) {
        this.emergencyAddress = emergencyAddress;
    }

    public String getEmergencyEmailAddress() {
        return emergencyEmailAddress;
    }

    public void setEmergencyEmailAddress(String emergencyEmailAddress) {
        this.emergencyEmailAddress = emergencyEmailAddress;
    }

    public String getEmergencyContactNo() {
        return emergencyContactNo;
    }

    public void setEmergencyContactNo(String emergencyContactNo) {
        this.emergencyContactNo = emergencyContactNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}