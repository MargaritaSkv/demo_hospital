package com.demo_hospital.controllers;

import com.demo_hospital.frontdesk.HospitalFrontDesk;
import com.demo_hospital.model.Patient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * HospitalController is a Spring MVC controller class that handles HTTP requests related to hospital operations.
 * It provides endpoints for patient registration, retrieval, admission, and release from rooms.
 *
 * @author mskvortsova
 * @since 1.0.0
 */

@RestController
// This annotation marks the class as a controller that handles incoming HTTP requests.
@RequestMapping("/api/hospital")
public class HospitalController {
    @Autowired
    private HospitalFrontDesk hospitalFrontDesk;

    // Endpoint to register a new patient
    @PostMapping("/patient/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientRegistrationRequest request) {

        // Extract patient registration data from the request
        Patient patientData = request.getPatient();

        // Create a new Patient object with the provided data
        Patient newPatient = new Patient(
                patientData.getFirstName(),
                patientData.getLastName(),
                patientData.getGender(),
                patientData.getDateOfBirth(),
                patientData.getAddress(),
                patientData.getPhoneNumber(),
                patientData.getSymptoms(),
                patientData.getTemperature(),
                patientData.isHasInjury()
        );
        hospitalFrontDesk.registerPatient(newPatient);

        // Return the newly created patient in the response
        return ResponseEntity.ok(newPatient);
    }


    // Endpoint to retrieve patient information by patient ID
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long patientId) {
        Patient patient = hospitalFrontDesk.getPatientById(patientId);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to admit a patient to a room
    @PostMapping("/patient/admit/{patientId}")
    public ResponseEntity<String> admitPatientToRoom(@PathVariable Long patientId) {
        String roomName;
        String roomNameToPrint;

        // Check patient condition and assign room accordingly
        Patient patient = hospitalFrontDesk.getPatientById(patientId);
        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found.");
        }
        if (patient.isHasInjury() || patient.getTemperature() > 38.00) {
            roomName = "firstAidStation";
            roomNameToPrint = "First Aid Station";
        } else {
            roomName = "generalPractitionerOffice";
            roomNameToPrint = "General Practitioner Office";
        }

        // Attempt to admit the patient to the room
        boolean admissionSuccess = hospitalFrontDesk.admitPatientToRoom(roomName, patient);

        if (admissionSuccess) {
            return ResponseEntity.ok("Patient admitted to " + roomNameToPrint +"."); // Respond with success message
        } else {
            return ResponseEntity.badRequest().body("Admission is impossible now. The room is busy with another patient.");
        }
    }


    // Endpoint to release a patient from a room
    @PostMapping("/release")
    public ResponseEntity<String> releasePatientFromRoom(@RequestBody ReleaseRequest request) {
        String roomName = request.getRoomName();

        boolean releaseSuccess = hospitalFrontDesk.releasePatientFromRoom(roomName);

        if (releaseSuccess) {
            return ResponseEntity.ok("Patient released from " + roomName);
        } else {
            return ResponseEntity.badRequest().body("Room is already empty.");
        }
    }


    // Registration request DTO class (for input data)
    @Data
    // By adding this annotation Lombok will automatically generate getter and setter methods.
    public static class PatientRegistrationRequest {
        private Patient patient;
    }

    // Release request DTO class (for input data)
    @Data
    public static class ReleaseRequest {
        private String roomName;
    }

}

