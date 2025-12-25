package com.example.backend.service;

import com.example.backend.entity.*;
import com.example.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LanguageRepository languageRepository;
    private final CertificateRepository certificateRepository;
    private final EmployeeLanguageRepository employeeLanguageRepository;
    private final EmployeeCertificateRepository employeeCertificateRepository;

    @Transactional
    public Employee create(Employee employee) {
        // Save employee
        Employee saved = new Employee();
        saved.setName(employee.getName());
        saved.setDob(employee.getDob());
        saved.setAddress(employee.getAddress());
        saved.setPhone(employee.getPhone());
        saved = employeeRepository.save(saved);

        // Add languages
        if (employee.getLanguages() != null && !employee.getLanguages().isEmpty()) {
            if (saved.getLanguages() == null) {
                saved.setLanguages(new java.util.ArrayList<>());
            }
            for (EmployeeLanguage el : employee.getLanguages()) {
                if (el != null && el.getLanguage() != null && el.getLanguage().getId() != null) {
                    Language language = languageRepository.findById(el.getLanguage().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Language not found: " + el.getLanguage().getId()));

                    EmployeeLanguage employeeLanguage = new EmployeeLanguage();
                    employeeLanguage.setEmployee(saved);
                    employeeLanguage.setLanguage(language);
                    EmployeeLanguage savedEl = employeeLanguageRepository.save(employeeLanguage);
                    saved.getLanguages().add(savedEl);
                }
            }
        }

        // Add certificates
        if (employee.getCertificates() != null && !employee.getCertificates().isEmpty()) {
            if (saved.getCertificates() == null) {
                saved.setCertificates(new java.util.ArrayList<>());
            }
            for (EmployeeCertificate ec : employee.getCertificates()) {
                if (ec != null && ec.getCertificate() != null && ec.getCertificate().getId() != null) {
                    Certificate certificate = certificateRepository.findById(ec.getCertificate().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Certificate not found: " + ec.getCertificate().getId()));

                    EmployeeCertificate employeeCertificate = new EmployeeCertificate();
                    employeeCertificate.setEmployee(saved);
                    employeeCertificate.setCertificate(certificate);
                    EmployeeCertificate savedEc = employeeCertificateRepository.save(employeeCertificate);
                    saved.getCertificates().add(savedEc);
                }
            }
        }

        return saved;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Employee not found with id: " + id));
    }

    @Transactional
    public Employee update(Long id, Employee updated) {
        Employee employee = getById(id);

        employee.setName(updated.getName());
        employee.setDob(updated.getDob());
        employee.setAddress(updated.getAddress());
        employee.setPhone(updated.getPhone());

        // Xóa tất cả languages và certificates hiện tại (sử dụng cascade)
        if (employee.getLanguages() != null && !employee.getLanguages().isEmpty()) {
            employee.getLanguages().clear(); // Cascade sẽ tự động xóa
        }
        if (employee.getCertificates() != null && !employee.getCertificates().isEmpty()) {
            employee.getCertificates().clear(); // Cascade sẽ tự động xóa
        }

        // Lưu employee để cascade xóa các bản ghi cũ
        employee = employeeRepository.save(employee);
        employeeRepository.flush(); // Flush

        // Khởi tạo lists nếu null
        if (employee.getLanguages() == null) {
            employee.setLanguages(new java.util.ArrayList<>());
        }
        if (employee.getCertificates() == null) {
            employee.setCertificates(new java.util.ArrayList<>());
        }

        // Thêm lại languages mới
        if (updated.getLanguages() != null && !updated.getLanguages().isEmpty()) {
            for (EmployeeLanguage el : updated.getLanguages()) {
                if (el != null && el.getLanguage() != null && el.getLanguage().getId() != null) {
                    Language language = languageRepository.findById(el.getLanguage().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Language not found: " + el.getLanguage().getId()));

                    EmployeeLanguage employeeLanguage = new EmployeeLanguage();
                    employeeLanguage.setEmployee(employee);
                    employeeLanguage.setLanguage(language);
                    EmployeeLanguage saved = employeeLanguageRepository.save(employeeLanguage);
                    employee.getLanguages().add(saved);
                }
            }
        }

        // Thêm lại certificates mới
        if (updated.getCertificates() != null && !updated.getCertificates().isEmpty()) {
            for (EmployeeCertificate ec : updated.getCertificates()) {
                if (ec != null && ec.getCertificate() != null && ec.getCertificate().getId() != null) {
                    Certificate certificate = certificateRepository.findById(ec.getCertificate().getId())
                            .orElseThrow(() -> new EntityNotFoundException("Certificate not found: " + ec.getCertificate().getId()));

                    EmployeeCertificate employeeCertificate = new EmployeeCertificate();
                    employeeCertificate.setEmployee(employee);
                    employeeCertificate.setCertificate(certificate);
                    EmployeeCertificate saved = employeeCertificateRepository.save(employeeCertificate);
                    employee.getCertificates().add(saved);
                }
            }
        }

        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        Employee employee = getById(id);
        employeeRepository.delete(employee);
    }
}
