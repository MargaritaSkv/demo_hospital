package com.demo_hospital.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * The Patient class models essential information about a hospital patient. Also, Patient instances are identified by unique identifiers.
 *
 * @author mskvortsova
 * @since 1.0.0
 */
@Entity
// This annotation is from Java Persistence API (JPA) and is used to mark the class as a JPA entity.
// It indicates that instances of this class can be persisted to a relational database.
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String symptoms;
    private double temperature; // Temperature in Celsius
    private boolean hasInjury;

    // Constructors
    public Patient(String firstName, String lastName, int age, String gender, Date dateOfBirth,
                   String address, String phoneNumber, String symptoms, double temperature, boolean hasInjury) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.symptoms = symptoms;
        this.temperature = temperature;
        this.hasInjury = hasInjury;
    }

    protected Patient() {
        // Added a no-argument constructor to satisfy JPA's requirements.
    }

    // Getters and Setters
    public Long getPatientId() {
        return patientId;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isHasInjury() {
        return hasInjury;
    }

    public void setHasInjury(boolean hasInjury) {
        this.hasInjury = hasInjury;
    }

}
