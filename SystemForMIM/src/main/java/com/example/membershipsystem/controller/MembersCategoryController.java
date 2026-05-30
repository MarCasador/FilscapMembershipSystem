package com.example.membershipsystem.controller;

import com.example.membershipsystem.model.MembersCategory;
import com.example.membershipsystem.repository.MembersCategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/MembersCategory")
@CrossOrigin
public class MembersCategoryController {

    private final MembersCategoryRepository categoryRepository;

    public MembersCategoryController(MembersCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<MembersCategory> getAllMembersCategory() {
        return categoryRepository.findAll();
    }
}