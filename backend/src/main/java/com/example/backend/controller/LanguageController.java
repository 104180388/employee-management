package com.example.backend.controller;

import com.example.backend.entity.Language;
import com.example.backend.service.LanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    // CREATE
    @PostMapping
    public ResponseEntity<Language> createLanguage(
            @Valid @RequestBody Language language) {

        Language saved = languageService.create(language);

        return ResponseEntity
                .created(URI.create("/api/languages/" + saved.getId()))
                .body(saved);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Long id) {
        return ResponseEntity.ok(languageService.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguage(
            @PathVariable Long id,
            @Valid @RequestBody Language language) {

        return ResponseEntity.ok(languageService.update(id, language));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        languageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
