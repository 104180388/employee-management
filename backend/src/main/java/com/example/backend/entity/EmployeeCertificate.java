package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "employee_certificate",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"employee_id", "certificate_id"})
        }
)
@Data
@NoArgsConstructor
public class EmployeeCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "certificate_id")
    private Certificate certificate;
}