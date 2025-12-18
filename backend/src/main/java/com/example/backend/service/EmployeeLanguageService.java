package com.example.backend.service;

import com.example.backend.entity.Employee;
import com.example.backend.entity.EmployeeLanguage;
import com.example.backend.entity.Language;
import com.example.backend.repository.EmployeeLanguageRepository;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.repository.LanguageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeLanguageService {

    private final EmployeeLanguageRepository employeeLanguageRepository;
    private final EmployeeRepository employeeRepository;
    private final LanguageRepository languageRepository;

    // GÁN LANGUAGE CHO EMPLOYEE
    public EmployeeLanguage assignLanguage(Long employeeId, Long languageId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Employee not found: " + employeeId));

        Language language = languageRepository.findById(languageId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Language not found: " + languageId));

        EmployeeLanguage employeeLanguage = new EmployeeLanguage();
        employeeLanguage.setEmployee(employee);
        employeeLanguage.setLanguage(language);

        return employeeLanguageRepository.save(employeeLanguage);
    }

    // GET ALL
    public List<EmployeeLanguage> getAll() {
        return employeeLanguageRepository.findAll();
    }

    // GET BY ID
    public EmployeeLanguage getById(Long id) {
        return employeeLanguageRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("EmployeeLanguage not found: " + id));
    }

    // DELETE
    public void delete(Long id) {
        EmployeeLanguage el = getById(id);
        employeeLanguageRepository.delete(el);
    }
}
