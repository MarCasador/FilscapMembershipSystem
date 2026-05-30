package com.example.membershipsystem.members;

import com.example.membershipsystem.model.Document;
import com.example.membershipsystem.repository.DocumentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin
public class DocumentController {

    private final DocumentRepository documentRepo;

    public DocumentController(DocumentRepository documentRepo) {
        this.documentRepo = documentRepo;
    }

    /* ================= UPLOAD ================= */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("memberId") Long memberId
    ) {
        try {
            System.out.println("UPLOAD HIT");
            System.out.println("memberId = " + memberId);

            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis()
                    + "_" + file.getOriginalFilename();

            Path filePath = uploadPath.resolve(fileName);
            file.transferTo(filePath.toFile());

            Document doc = new Document();
            doc.setMemberId(memberId);
            doc.setFileName(fileName);
            doc.setFilePath(filePath.toString());
            doc.setUploadDate(LocalDateTime.now()); // 🔥 FIX IMPORTANT

            Document saved = documentRepo.save(doc);

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Upload failed: " + e.getMessage());
        }
    }

    /* ================= LIST ================= */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable Long memberId) {
        return ResponseEntity.ok(documentRepo.findByMemberId(memberId));
    }

    /* ================= DELETE (FIXED ROUTE) ================= */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long id) {

        return documentRepo.findById(id)
                .map(doc -> {
                    try {
                        Files.deleteIfExists(Paths.get(doc.getFilePath()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    documentRepo.delete(doc);
                    return ResponseEntity.ok("deleted");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}