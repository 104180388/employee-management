package com.example.backend.controller;

import com.example.backend.entity.EmployeeLanguage;
import com.example.backend.service.EmployeeLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee-languages")
@RequiredArgsConstructor
public class EmployeeLanguageController {

    private final EmployeeLanguageService employeeLanguageService;

    // ASSIGN LANGUAGE TO EMPLOYEE
    @PostMapping
    public ResponseEntity<EmployeeLanguage> assignLanguage(
            @RequestParam Long employeeId,
            @RequestParam Long languageId) {

        EmployeeLanguage saved =
                employeeLanguageService.assignLanguage(employeeId, languageId);

        return ResponseEntity
                .created(URI.create("/api/employee-languages/" + saved.getId()))
                .body(saved);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<EmployeeLanguage>> getAll() {
        return ResponseEntity.ok(employeeLanguageService.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeLanguage> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeLanguageService.getById(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeLanguageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}