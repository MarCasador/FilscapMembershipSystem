package com.example.membershipsystem.repository;

import com.example.membershipsystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUserId(Long userId);

    @Query(value = """
    SELECT *
    FROM members m
    WHERE 1=1

    AND (:status IS NULL OR :status = '' OR LOWER(m.status) = LOWER(:status))

    AND (:category IS NULL OR m.category_id = :category)

    AND (:date IS NULL OR DATE(m.created_date) = :date)

    AND (:month IS NULL OR :month = '' OR DATE_FORMAT(m.created_date, '%Y-%m') = :month)

    AND (:quarter IS NULL OR :quarter = '' OR
        (
            (:quarter = 'Q1' AND MONTH(m.created_date) BETWEEN 1 AND 3) OR
            (:quarter = 'Q2' AND MONTH(m.created_date) BETWEEN 4 AND 6) OR
            (:quarter = 'Q3' AND MONTH(m.created_date) BETWEEN 7 AND 9) OR
            (:quarter = 'Q4' AND MONTH(m.created_date) BETWEEN 10 AND 12)
        )
    )
""", nativeQuery = true)
    List<Member> getFilteredReport(
            @Param("status") String status,
            @Param("category") Long category,
            @Param("date") LocalDate date,
            @Param("month") String month,
            @Param("quarter") String quarter
    );
}