package com.demo_hospital.frontdesk;

import com.demo_hospital.model.Patient;
import com.demo_hospital.repository.PatientRepository;
import com.demo_hospital.rooms.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private PatientRepository patientRepository;

    // This constructor injection is a way to provide the HospitalFrontDesk class with access to various types of rooms available in the hospital
    @Autowired
    public HospitalFrontDesk(Map<String, Room> rooms) {
        this.rooms = rooms;
    }


    public Patient registerPatient(Patient newPatient) {

        // Add the patient to the database
        patientRepository.saveAndFlush(newPatient);

        return newPatient;
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    public boolean admitPatientToRoom(String roomName, Patient patient) {
        Room room = rooms.get(roomName);
        if (room != null) {
            if (room.admitPatient(patient)) {
                return true; // Indicate that the admission was successful
            } else return false;
        }
        return false;
    }

    public boolean releasePatientFromRoom(String roomName) {
        Room room = rooms.get(roomName);
        if (room != null) {
            if (room.releasePatient() != null) {
                return true; // Indicate that the release was successful
            } else return false;
        }
        return false;
    }

}
