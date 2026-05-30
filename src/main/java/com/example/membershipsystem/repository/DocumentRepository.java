package com.example.membershipsystem.repository;

import com.example.membershipsystem.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByMemberId(Long memberId);
}