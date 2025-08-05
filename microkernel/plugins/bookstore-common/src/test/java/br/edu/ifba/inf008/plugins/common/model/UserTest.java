package br.edu.ifba.inf008.plugins.common.model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Simple unit tests for the User model class.
 */
class UserTest {

    @Test
    @DisplayName("Should create a user and correctly retrieve its properties")
    void testUserCreationAndGetters() {
        // Arrange: Define the data for our new user
        String expectedName = "John Doe";
        String expectedEmail = "john.doe@example.com";

        // Act: Create a new User instance using the constructor
        User user = new User(expectedName, expectedEmail);

        // Assert: Check if the getters return the expected values
        assertEquals(expectedName, user.getName(), "The name should match the one provided in the constructor.");
        assertEquals(expectedEmail, user.getEmail(), "The email should match the one provided in the constructor.");
        assertEquals(LocalDate.now(), user.getRegistrationDate(), "The registration date should be set to the current date.");
    }

    @Test
    @DisplayName("Should update user properties using setters")
    void testUserSetters() {
        // Arrange: Create a blank user and define the new data
        User user = new User();
        int newId = 10;
        String newName = "Jane Smith";
        String newEmail = "jane.smith@example.com";
        LocalDate newDate = LocalDate.of(2025, 1, 15);

        // Act: Use the setter methods to update the user's properties
        user.setId(newId);
        user.setName(newName);
        user.setEmail(newEmail);
        user.setRegisteredAt(newDate);

        // Assert: Check if the getters now return the new values
        assertEquals(newId, user.getId(), "The ID should be updated by the setter.");
        assertEquals(newName, user.getName(), "The name should be updated by the setter.");
        assertEquals(newEmail, user.getEmail(), "The email should be updated by the setter.");
        assertEquals(newDate, user.getRegistrationDate(), "The registration date should be updated by the setter.");
    }
}