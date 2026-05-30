package com.example.membershipsystem.repository;

import com.example.membershipsystem.model.MembersCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersCategoryRepository
        extends JpaRepository<MembersCategory, Long> {

}