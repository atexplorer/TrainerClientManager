package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.dto.CreateAppUserDto;
import org.atexplorer.TrainerClientManager.entity.*;
import org.atexplorer.TrainerClientManager.factory.ProfileFactory;
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
    public void addUser(CreateAppUserDto request) {
        AppUser appUser = addAppUser(request);

        UserProfile userProfile = ProfileFactory.createUserProfile(request);
        userProfile.setAppUser(appUser);

        userProfileRepository.save(userProfile);
    }

    private AppUser addAppUser(CreateAppUserDto request){
        if(appUserRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username taken. Please select provide new username");
        }

        AppUser appUser = new AppUser();
        //this needs moved to a mapper
        appUser.setUsername(request.getUsername());
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setAuthority("ROLE_USER");


        appUserRepository.save(appUser);

        return appUser;
    }


}
