package com.demo_hospital.repository;

import com.demo_hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The PatientRepository interface defines data access operations for the "patients" table.
 * It extends JpaRepository, which provides CRUD (Create, Read, Update, Delete) operations for Patient entities.
 * Spring Data JPA will automatically generate the implementation of this repository interface.
 * Custom queries can be also defined by adding methods.
 *
 * @author mskvortsova
 * @since 1.0.0
 */

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByLastName(String lastName);
}