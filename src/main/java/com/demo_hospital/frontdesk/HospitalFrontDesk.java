package com.demo_hospital.frontdesk;

import com.demo_hospital.model.Patient;
import com.demo_hospital.rooms.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The HospitalFrontDesk class is responsible for managing patient admissions and releases to various hospital rooms.
 * It acts as a central point of control for routing patients to appropriate rooms based on their needs and room availability.
 *
 * @author mskvortsova
 * @since 1.0.0
 */
@Service
// This Spring annotation tells that it is a Spring service bean, and it can be managed by the Spring container.
public class HospitalFrontDesk {
    private final Map<String, Room> rooms;
    private final Map<Long, Patient> patients = new HashMap<>();

    @Autowired
    public HospitalFrontDesk(Map<String, Room> rooms) {
        this.rooms = rooms;
    }

    public Patient registerPatient(String firstName, String lastName, int age, String gender,
                                   Date dateOfBirth, String address, String phoneNumber,
                                   String symptoms, double temperature, boolean hasInjury) {
        // Create a new patient object
        Patient patient = new Patient(firstName, lastName, age, gender, dateOfBirth,
                address, phoneNumber, symptoms, temperature, hasInjury);

        // Add the patient to the database
        patients.put(patient.getPatientId(), patient);

        return patient;
    }

    public Patient getPatientById(Long patientId) {
        return patients.get(patientId);
    }

    public boolean admitPatientToRoom(String roomName, Patient patient) {
        Room room = rooms.get(roomName);
        if (room != null) {
            room.admitPatient(patient);
            return true; // Indicate that the admission was successful
        }
        return false;
    }

    public boolean releasePatientFromRoom(String roomName) {
        Room room = rooms.get(roomName);
        if (room != null) {
            room.releasePatient();
            return true; // Indicate that the release was successful
        }
        return false;
    }

}
