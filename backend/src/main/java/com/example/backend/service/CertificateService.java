package com.example.backend.service;

import com.example.backend.entity.Certificate;
import com.example.backend.repository.CertificateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;

    // CREATE
    public Certificate create(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    // GET ALL
    public List<Certificate> getAll() {
        return certificateRepository.findAll();
    }

    // GET BY ID
    public Certificate getById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Certificate not found with id: " + id));
    }

    // UPDATE
    public Certificate update(Long id, Certificate updated) {
        Certificate certificate = getById(id);

        certificate.setName(updated.getName());

        return certificateRepository.save(certificate);
    }

    // DELETE
    public void delete(Long id) {
        Certificate certificate = getById(id);
        certificateRepository.delete(certificate);
    }
}
