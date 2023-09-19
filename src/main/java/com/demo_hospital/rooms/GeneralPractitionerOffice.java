package com.demo_hospital.rooms;

import org.springframework.stereotype.Component;

/**
 * A class representing a General Practitioner Office in a hospital.
 *
 * @author mskvortsova
 * @since 1.0.0
 */
@Component
// This Spring annotation tells Spring to treat these classes as Spring beans. As a result, Spring will manage their lifecycle and make them available for dependency injection.
public class GeneralPractitionerOffice extends AbstractRoom {
    public GeneralPractitionerOffice() {
        super("General Practitioner Office"); // Call the constructor of the base class with the room name
    }
}
