package com.example.backend.service;

import com.example.backend.entity.Certificate;
import com.example.backend.entity.Employee;
import com.example.backend.entity.EmployeeCertificate;
import com.example.backend.repository.CertificateRepository;
import com.example.backend.repository.EmployeeCertificateRepository;
import com.example.backend.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeCertificateService {

    private final EmployeeCertificateRepository employeeCertificateRepository;
    private final EmployeeRepository employeeRepository;
    private final CertificateRepository certificateRepository;

    // GÁN CERTIFICATE CHO EMPLOYEE
    public EmployeeCertificate assignCertificate(Long employeeId, Long certificateId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Employee not found: " + employeeId));

        Certificate certificate = certificateRepository.findById(certificateId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Certificate not found: " + certificateId));

        EmployeeCertificate employeeCertificate = new EmployeeCertificate();
        employeeCertificate.setEmployee(employee);
        employeeCertificate.setCertificate(certificate);

        return employeeCertificateRepository.save(employeeCertificate);
    }

    // GET ALL
    public List<EmployeeCertificate> getAll() {
        return employeeCertificateRepository.findAll();
    }

    // GET BY ID
    public EmployeeCertificate getById(Long id) {
        return employeeCertificateRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("EmployeeCertificate not found: " + id));
    }

    // DELETE
    public void delete(Long id) {
        EmployeeCertificate ec = getById(id);
        employeeCertificateRepository.delete(ec);
    }
}
