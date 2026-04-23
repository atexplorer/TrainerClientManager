package org.atexplorer.TrainerClientManager.factory;

import org.atexplorer.TrainerClientManager.dto.AppUserDto;
import org.atexplorer.TrainerClientManager.dto.ClientProfileDto;
import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;
import org.atexplorer.TrainerClientManager.dto.TrainerProfileDto;
import org.atexplorer.TrainerClientManager.entity.AppUser;
import org.atexplorer.TrainerClientManager.entity.ClientProfile;
import org.atexplorer.TrainerClientManager.entity.TrainerProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProfileFactoryTest {

    private ProfileFactory factory;
    private CreateClientAccountDto clientRequest;
    private CreateTrainerAccountDto trainerRequest;

    @BeforeEach
    void setUp() {
        factory = new ProfileFactory();

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

    // --- mapProfileToDto ---

    private AppUser makeAppUser(String username) {
        return new AppUser(username, "password", "ROLE_USER");
    }

    @Test
    void mapProfileToDto_trainerProfile_returnsTrainerProfileDto() {
        TrainerProfile trainer = new TrainerProfile();
        trainer.setAppUser(makeAppUser("traineruser"));
        trainer.setFirstName("John");
        trainer.setMiddleName("B");
        trainer.setLastName("Smith");
        trainer.setSuffix(null);
        trainer.setEmail("trainer@example.com");
        trainer.setActive(true);
        trainer.setClients(List.of());

        AppUserDto result = factory.mapProfileToDto(trainer);

        assertInstanceOf(TrainerProfileDto.class, result);
        TrainerProfileDto dto = (TrainerProfileDto) result;
        assertEquals("traineruser", dto.getUsername());
        assertEquals("John", dto.getFirstName());
        assertEquals("B", dto.getMiddleName());
        assertEquals("Smith", dto.getLastName());
        assertNull(dto.getSuffix());
        assertEquals("trainer@example.com", dto.getEmail());
        assertTrue(dto.isActive());
        assertEquals("Trainer", dto.getUserType());
        assertNotNull(dto.getClientProfiles());
    }

    @Test
    void mapProfileToDto_clientProfile_returnsClientProfileDto() {
        ClientProfile client = new ClientProfile();
        client.setAppUser(makeAppUser("clientuser"));
        client.setFirstName("Jane");
        client.setMiddleName("A");
        client.setLastName("Doe");
        client.setSuffix("Jr.");
        client.setEmail("client@example.com");
        client.setActive(true);
        client.setGoals(new String[]{"Lose weight", "Build strength"});

        AppUserDto result = factory.mapProfileToDto(client);

        assertInstanceOf(ClientProfileDto.class, result);
        ClientProfileDto dto = (ClientProfileDto) result;
        assertEquals("clientuser", dto.getUsername());
        assertEquals("Jane", dto.getFirstName());
        assertEquals("A", dto.getMiddleName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("Jr.", dto.getSuffix());
        assertEquals("client@example.com", dto.getEmail());
        assertTrue(dto.isActive());
        assertEquals("Client", dto.getUserType());
        assertArrayEquals(new String[]{"Lose weight", "Build strength"}, dto.getGoals());
    }
}
