package com.example.membershipsystem.repository;

import com.example.membershipsystem.model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyContactRepository
        extends JpaRepository<EmergencyContact, Long> {

    // GET ALL CONTACTS BY MEMBER ID
    List<EmergencyContact> findByMemberId(Long memberId);
}