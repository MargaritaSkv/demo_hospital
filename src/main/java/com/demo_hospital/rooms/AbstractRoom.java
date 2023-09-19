package com.demo_hospital.rooms;

import com.demo_hospital.model.Patient;

/**
 * This abstract class serves as the blueprint for various types of hospital rooms.
 * It encapsulates common properties and behaviors of hospital rooms, allowing concrete room implementations to extend and specialize their functionality.
 *
 * @author mskvortsova
 * @since 1.0.0
 */
public abstract class AbstractRoom implements Room {
    private final String roomName;
    private boolean available;
    private Patient currentPatient;

    protected AbstractRoom(String roomName) {
        this.roomName = roomName;
        this.available = true;
        this.currentPatient = null;
    }

    @Override
    public String getRoomName() {
        return roomName;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    // Return true if the patient is admitted successfully, or false if the room is already busy.
    public boolean admitPatient(Patient patient) {
        if (available) {
            currentPatient = patient;
            available = false;
            return true;
        }
        return false;
    }

    @Override
    // Return the released patient, or null if the room is already available.
    public Patient releasePatient() {
        if (!available) {
            Patient releasedPatient = currentPatient;
            currentPatient = null;
            available = true;
            return releasedPatient;
        }
        return null;
    }
}
