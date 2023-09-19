package com.demo_hospital.rooms;

import com.demo_hospital.model.Patient;

/**
 * Represents a hospital room where patients can be admitted and released.
 * Rooms have names, can be checked for availability, and allow admitting and releasing patients.
 *
 * @author mskvortsova
 * @since 1.0.0
 */
public interface Room {
    String getRoomName();

    boolean isAvailable();

    boolean admitPatient(Patient patient);

    Patient releasePatient();
}