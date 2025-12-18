package com.example.backend.repository;

import com.example.backend.entity.EmployeeLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLanguageRepository extends JpaRepository<EmployeeLanguage, Long> {
    boolean existsByEmployeeIdAndLanguageId(Long employeeId, Long languageId);
}

