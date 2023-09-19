package controllers;

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
    private final HospitalFrontDesk hospitalFrontDesk;

    @Autowired
    public HospitalController(HospitalFrontDesk hospitalFrontDesk) {
        this.hospitalFrontDesk = hospitalFrontDesk;
    }

    // Endpoint to register a new patient
    @PostMapping("/patient/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientRegistrationRequest request) {

        // Extract patient registration data from the request
        Patient patientData = request.getPatient();

        // Create a new Patient object with the provided data
        Patient newPatient = new Patient(
                patientData.getFirstName(),
                patientData.getLastName(),
                patientData.getAge(),
                patientData.getGender(),
                patientData.getDateOfBirth(),
                patientData.getAddress(),
                patientData.getPhoneNumber(),
                patientData.getSymptoms(),
                patientData.getTemperature(),
                patientData.isHasInjury()
        );

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
    @PostMapping("/admit")
    public ResponseEntity<String> admitPatientToRoom(@RequestBody AdmissionRequest request) {
        Long patientId = request.getPatientId();
        String roomName;

        // Check patient condition and assign room accordingly
        Patient patient = hospitalFrontDesk.getPatientById(patientId);
        if (patient == null) {
            return ResponseEntity.badRequest().body("Patient not found.");
        }
        if (patient.isHasInjury() || patient.getTemperature() > 38.00) {
            roomName = "FirstAidStation";
        } else {
            roomName = "GeneralPractitionerOffice";
        }

        // Attempt to admit the patient to the room
        boolean admissionSuccess = hospitalFrontDesk.admitPatientToRoom(roomName, patient);

        if (admissionSuccess) {
            return ResponseEntity.ok("Patient admitted to " + roomName); // Respond with success message
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

    // Admission request DTO class (for input data)
    @Data
    public static class AdmissionRequest {
        private Long patientId;

        // Constructor that takes a Patient object and extracts the ID
        public AdmissionRequest(Patient patient) {
            this.patientId = patient.getPatientId();
        }
    }

    // Release request DTO class (for input data)
    @Data
    public static class ReleaseRequest {
        private String roomName;
    }

}

