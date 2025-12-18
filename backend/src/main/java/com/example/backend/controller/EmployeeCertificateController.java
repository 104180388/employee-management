package com.example.backend.controller;

import com.example.backend.entity.EmployeeCertificate;
import com.example.backend.service.EmployeeCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employee-certificates")
@RequiredArgsConstructor
public class EmployeeCertificateController {

    private final EmployeeCertificateService employeeCertificateService;

    // ASSIGN CERTIFICATE TO EMPLOYEE
    @PostMapping
    public ResponseEntity<EmployeeCertificate> assignCertificate(
            @RequestParam Long employeeId,
            @RequestParam Long certificateId) {

        EmployeeCertificate saved =
                employeeCertificateService.assignCertificate(employeeId, certificateId);

        return ResponseEntity
                .created(URI.create("/api/employee-certificates/" + saved.getId()))
                .body(saved);
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<EmployeeCertificate>> getAll() {
        return ResponseEntity.ok(employeeCertificateService.getAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeCertificate> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeCertificateService.getById(id));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeCertificateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
