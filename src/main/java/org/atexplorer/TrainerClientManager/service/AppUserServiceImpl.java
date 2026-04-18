package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.dto.CreateAppUserRequest;
import org.atexplorer.TrainerClientManager.entity.*;
import org.atexplorer.TrainerClientManager.repository.AppUserRepository;
import org.atexplorer.TrainerClientManager.repository.UserProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService{

    private final AppUserRepository appUserRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImpl(AppUserRepository appUserRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addUser(CreateAppUserRequest request) {
        UserType userType = UserType.valueOf(request.getUserType());
        AppUser appUser = addAppUser(request, userType.toString());
        UserProfile userProfile = switch (userType){
            case CLIENT -> addClient();
            case TRAINER -> addTrainer();
        };

        //this probably needs moved to a mapper
        userProfile.setEmail(request.getEmail());
        userProfile.setName(request.getFirstName() + " " + request.getLastName());
        userProfile.setAppUser(appUser);
        userProfile.setActive(true);
        userProfileRepository.save(userProfile);
    }

    private AppUser addAppUser(CreateAppUserRequest request, String authority){
        if(appUserRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username taken. Please select provide new username");
        }

        AppUser appUser = new AppUser();

        //this needs moved to a mapper
        appUser.setUsername(request.getUsername());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setAuthority(authority);


        appUserRepository.save(appUser);

        return appUser;
    }

    public TrainerProfile addTrainer(){
        return new TrainerProfile();
    }

    public ClientProfile addClient(){
        return new ClientProfile();
    }


}
