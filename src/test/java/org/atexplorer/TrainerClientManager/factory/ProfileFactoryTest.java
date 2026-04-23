package org.atexplorer.TrainerClientManager.factory;

import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;
import org.atexplorer.TrainerClientManager.entity.ClientProfile;
import org.atexplorer.TrainerClientManager.entity.TrainerProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileFactoryTest {

    private CreateClientAccountDto clientRequest;
    private CreateTrainerAccountDto trainerRequest;

    @BeforeEach
    void setUp() {
        clientRequest = new CreateClientAccountDto();
        clientRequest.setUsername("clientuser");
        clientRequest.setPassword("password123");
        clientRequest.setEmail("client@example.com");
        clientRequest.setFirstName("Jane");
        clientRequest.setMiddleName("A");
        clientRequest.setLastName("Doe");
        clientRequest.setSuffix("Jr.");
        clientRequest.setGoals(new String[]{"Lose weight", "Build strength"});

        trainerRequest = new CreateTrainerAccountDto();
        trainerRequest.setUsername("traineruser");
        trainerRequest.setPassword("password123");
        trainerRequest.setEmail("trainer@example.com");
        trainerRequest.setFirstName("John");
        trainerRequest.setMiddleName("B");
        trainerRequest.setLastName("Smith");
        trainerRequest.setSuffix(null);
        trainerRequest.setCertifications(new String[]{"NASM", "ACE"});
    }

    @Test
    void createClientProfile_mapsCommonFields() {
        ClientProfile profile = ProfileFactory.createClientProfile(clientRequest);

        assertEquals("client@example.com", profile.getEmail());
        assertEquals("Jane", profile.getFirstName());
        assertEquals("A", profile.getMiddleName());
        assertEquals("Doe", profile.getLastName());
        assertEquals("Jr.", profile.getSuffix());
        assertEquals("Jane Doe", profile.getName());
    }

    @Test
    void createClientProfile_setsActiveTrue() {
        ClientProfile profile = ProfileFactory.createClientProfile(clientRequest);
        assertTrue(profile.isActive());
    }

    @Test
    void createClientProfile_setsGoals() {
        ClientProfile profile = ProfileFactory.createClientProfile(clientRequest);
        assertArrayEquals(new String[]{"Lose weight", "Build strength"}, profile.getGoals());
    }

    @Test
    void createClientProfile_withNullGoals_setsNullGoals() {
        clientRequest.setGoals(null);
        ClientProfile profile = ProfileFactory.createClientProfile(clientRequest);
        assertNull(profile.getGoals());
    }

    @Test
    void createTrainerProfile_mapsCommonFields() {
        TrainerProfile profile = ProfileFactory.createTrainerProfile(trainerRequest);

        assertEquals("trainer@example.com", profile.getEmail());
        assertEquals("John", profile.getFirstName());
        assertEquals("B", profile.getMiddleName());
        assertEquals("Smith", profile.getLastName());
        assertNull(profile.getSuffix());
        assertEquals("John Smith", profile.getName());
    }

    @Test
    void createTrainerProfile_setsActiveTrue() {
        TrainerProfile profile = ProfileFactory.createTrainerProfile(trainerRequest);
        assertTrue(profile.isActive());
    }

    @Test
    void createTrainerProfile_setsCertifications() {
        TrainerProfile profile = ProfileFactory.createTrainerProfile(trainerRequest);
        assertArrayEquals(new String[]{"NASM", "ACE"}, profile.getCertifications());
    }

    @Test
    void createTrainerProfile_withNullCertifications_setsNullCertifications() {
        trainerRequest.setCertifications(null);
        TrainerProfile profile = ProfileFactory.createTrainerProfile(trainerRequest);
        assertNull(profile.getCertifications());
    }
}
