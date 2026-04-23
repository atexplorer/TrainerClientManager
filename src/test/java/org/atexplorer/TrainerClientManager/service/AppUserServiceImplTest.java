package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;
import org.atexplorer.TrainerClientManager.entity.AppUser;
import org.atexplorer.TrainerClientManager.entity.ClientProfile;
import org.atexplorer.TrainerClientManager.entity.TrainerProfile;
import org.atexplorer.TrainerClientManager.repository.AppUserRepository;
import org.atexplorer.TrainerClientManager.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserServiceImpl appUserService;

    private CreateClientAccountDto clientRequest;
    private CreateTrainerAccountDto trainerRequest;

    @BeforeEach
    void setUp() {
        clientRequest = new CreateClientAccountDto();
        clientRequest.setUsername("clientuser");
        clientRequest.setPassword("plainpassword");
        clientRequest.setEmail("client@example.com");
        clientRequest.setFirstName("Jane");
        clientRequest.setLastName("Doe");
        clientRequest.setGoals(new String[]{"Cardio"});

        trainerRequest = new CreateTrainerAccountDto();
        trainerRequest.setUsername("traineruser");
        trainerRequest.setPassword("plainpassword");
        trainerRequest.setEmail("trainer@example.com");
        trainerRequest.setFirstName("John");
        trainerRequest.setLastName("Smith");
        trainerRequest.setCertifications(new String[]{"NASM"});
    }

    @Test
    void createClientAccount_savesAppUserAndClientProfile() {
        when(appUserRepository.findByUsername("clientuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");

        appUserService.createClientAccount(clientRequest);

        ArgumentCaptor<AppUser> appUserCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(appUserCaptor.capture());
        AppUser savedUser = appUserCaptor.getValue();
        assertEquals("clientuser", savedUser.getUsername());
        assertEquals("encodedpassword", savedUser.getPassword());
        assertEquals("ROLE_USER", savedUser.getAuthority());

        ArgumentCaptor<ClientProfile> profileCaptor = ArgumentCaptor.forClass(ClientProfile.class);
        verify(userProfileRepository).save(profileCaptor.capture());
        ClientProfile savedProfile = profileCaptor.getValue();
        assertEquals("client@example.com", savedProfile.getEmail());
        assertArrayEquals(new String[]{"Cardio"}, savedProfile.getGoals());
        assertTrue(savedProfile.isActive());
    }

    @Test
    void createClientAccount_throwsWhenUsernameTaken() {
        when(appUserRepository.findByUsername("clientuser")).thenReturn(Optional.of(new AppUser()));

        assertThrows(RuntimeException.class, () -> appUserService.createClientAccount(clientRequest));

        verify(appUserRepository, never()).save(any());
        verify(userProfileRepository, never()).save(any());
    }

    @Test
    void createClientAccount_encodesPassword() {
        when(appUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");

        appUserService.createClientAccount(clientRequest);

        verify(passwordEncoder).encode("plainpassword");
        ArgumentCaptor<AppUser> captor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(captor.capture());
        assertEquals("encodedpassword", captor.getValue().getPassword());
    }

    @Test
    void createTrainerAccount_savesAppUserAndTrainerProfile() {
        when(appUserRepository.findByUsername("traineruser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainpassword")).thenReturn("encodedpassword");

        appUserService.createTrainerAccount(trainerRequest);

        ArgumentCaptor<AppUser> appUserCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(appUserCaptor.capture());
        assertEquals("traineruser", appUserCaptor.getValue().getUsername());

        ArgumentCaptor<TrainerProfile> profileCaptor = ArgumentCaptor.forClass(TrainerProfile.class);
        verify(userProfileRepository).save(profileCaptor.capture());
        TrainerProfile savedProfile = profileCaptor.getValue();
        assertEquals("trainer@example.com", savedProfile.getEmail());
        assertArrayEquals(new String[]{"NASM"}, savedProfile.getCertifications());
        assertTrue(savedProfile.isActive());
    }

    @Test
    void createTrainerAccount_throwsWhenUsernameTaken() {
        when(appUserRepository.findByUsername("traineruser")).thenReturn(Optional.of(new AppUser()));

        assertThrows(RuntimeException.class, () -> appUserService.createTrainerAccount(trainerRequest));

        verify(appUserRepository, never()).save(any());
        verify(userProfileRepository, never()).save(any());
    }

    @Test
    void updateAssignedTrainer_setsTrainerOnClientProfile() {
        TrainerProfile trainer = new TrainerProfile();
        ClientProfile client = new ClientProfile();

        when(userProfileRepository.findTrainerProfileByAppUserUsername("trainer1")).thenReturn(Optional.of(trainer));
        when(userProfileRepository.findClientProfileByAppUserUsername("client1")).thenReturn(Optional.of(client));

        appUserService.updateAssignedTrainer("client1", "trainer1");

        assertEquals(trainer, client.getTrainer());
        verify(userProfileRepository).save(client);
    }

    @Test
    void updateAssignedTrainer_throwsWhenTrainerNotFound() {
        when(userProfileRepository.findTrainerProfileByAppUserUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> appUserService.updateAssignedTrainer("client1", "unknown"));

        verify(userProfileRepository, never()).save(any());
    }

    @Test
    void updateAssignedTrainer_throwsWhenClientNotFound() {
        TrainerProfile trainer = new TrainerProfile();
        when(userProfileRepository.findTrainerProfileByAppUserUsername("trainer1")).thenReturn(Optional.of(trainer));
        when(userProfileRepository.findClientProfileByAppUserUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> appUserService.updateAssignedTrainer("unknown", "trainer1"));

        verify(userProfileRepository, never()).save(any());
    }
}
