package com.example.backend.controller;

import com.example.backend.entity.Certificate;
import com.example.backend.service.CertificateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    // CREATE
    @PostMapping
    public ResponseEntity<Certificate> createCertificate(
            @Valid @RequestBody Certificate certificate) {

        Certificate saved = certificateService.create(certificate);

        return ResponseEntity
                .created(URI.create("/api/certificates/" + saved.getId()))
                .body(saved);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        return ResponseEntity.ok(certificateService.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificateById(@PathVariable Long id) {
        return ResponseEntity.ok(certificateService.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Certificate> updateCertificate(
            @PathVariable Long id,
            @Valid @RequestBody Certificate certificate) {

        return ResponseEntity.ok(
                certificateService.update(id, certificate)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable Long id) {
        certificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
