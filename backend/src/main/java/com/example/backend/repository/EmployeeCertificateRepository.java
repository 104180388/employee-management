package com.example.backend.repository;

import com.example.backend.entity.EmployeeCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCertificateRepository extends JpaRepository<EmployeeCertificate, Long> {
    boolean existsByEmployeeIdAndCertificateId(Long employeeId, Long certificateId);
}