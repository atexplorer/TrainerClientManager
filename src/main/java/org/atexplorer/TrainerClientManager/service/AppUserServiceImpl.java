package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.dto.CreateAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;
import org.atexplorer.TrainerClientManager.entity.*;
import org.atexplorer.TrainerClientManager.factory.ProfileFactory;
import org.atexplorer.TrainerClientManager.repository.AppUserRepository;
import org.atexplorer.TrainerClientManager.repository.UserProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createClientAccount(CreateClientAccountDto request) {
        AppUser appUser = addAppUser(request);
        ClientProfile profile = ProfileFactory.createClientProfile(request);
        profile.setAppUser(appUser);
        userProfileRepository.save(profile);
    }

    @Override
    public void createTrainerAccount(CreateTrainerAccountDto request) {
        AppUser appUser = addAppUser(request);
        TrainerProfile profile = ProfileFactory.createTrainerProfile(request);
        profile.setAppUser(appUser);
        userProfileRepository.save(profile);
    }

    @Override
    public void updateAssignedTrainer(String clientUsername, String trainerUsername) {
        TrainerProfile trainerProfile = userProfileRepository.findTrainerProfileByAppUserUsername(trainerUsername).orElseThrow();
        ClientProfile clientProfile = userProfileRepository.findClientProfileByAppUserUsername(clientUsername).orElseThrow();
        clientProfile.setTrainer(trainerProfile);
        userProfileRepository.save(clientProfile);
    }

    @Override
    public void addToClientList(String clientUsername, String trainerUsername) {

    }

    private AppUser addAppUser(CreateAccountDto request) {
        if (appUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username taken. Please provide a new username.");
        }

        AppUser appUser = new AppUser();
        appUser.setUsername(request.getUsername());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setAuthority("ROLE_USER");

        appUserRepository.save(appUser);

        return appUser;
    }
}
