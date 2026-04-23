package org.atexplorer.TrainerClientManager.factory;

import org.atexplorer.TrainerClientManager.dto.AppUserDto;
import org.atexplorer.TrainerClientManager.dto.ClientProfileDto;
import org.atexplorer.TrainerClientManager.dto.CreateAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;
import org.atexplorer.TrainerClientManager.dto.TrainerProfileDto;
import org.atexplorer.TrainerClientManager.entity.ClientProfile;
import org.atexplorer.TrainerClientManager.entity.TrainerProfile;
import org.atexplorer.TrainerClientManager.entity.UserProfile;

public class ProfileFactory {

    public static ClientProfile createClientProfile(CreateClientAccountDto request) {
        ClientProfile profile = new ClientProfile();
        applyCommonFields(profile, request);
        profile.setGoals(request.getGoals());
        return profile;
    }

    public static TrainerProfile createTrainerProfile(CreateTrainerAccountDto request) {
        TrainerProfile profile = new TrainerProfile();
        applyCommonFields(profile, request);
        profile.setCertifications(request.getCertifications());
        return profile;
    }

    private static void applyCommonFields(UserProfile profile, CreateAccountDto request) {
        profile.setEmail(request.getEmail());
        profile.setFirstName(request.getFirstName());
        profile.setMiddleName(request.getMiddleName());
        profile.setLastName(request.getLastName());
        profile.setSuffix(request.getSuffix());
        profile.setName(request.getFirstName() + " " + request.getLastName());
        profile.setActive(true);
    }

    public AppUserDto mapProfileToDto(UserProfile userProfile){
        AppUserDto appUserDto = new AppUserDto();

        baseProfileToDto(appUserDto, userProfile);

        return switch (userProfile){
            case TrainerProfile t -> trainerToDto(appUserDto, t);
            case ClientProfile c -> clientToDto(appUserDto, c);
            default -> appUserDto;
        };
    }

    private void baseProfileToDto(AppUserDto appUserDto, UserProfile userProfile){
        appUserDto.setUsername(userProfile.getAppUser().getUsername());
        appUserDto.setFirstName(userProfile.getFirstName());
        appUserDto.setMiddleName(userProfile.getMiddleName());
        appUserDto.setLastName(userProfile.getLastName());
        appUserDto.setSuffix(userProfile.getSuffix());
        appUserDto.setEmail(userProfile.getEmail());
        appUserDto.setActive(userProfile.isActive());
    }

    //I need to extend the AppUserDto to return a TrainerProfileDto
    private AppUserDto trainerToDto(AppUserDto appUserDto, TrainerProfile trainerProfile){
        TrainerProfileDto trainerProfileDto = (TrainerProfileDto) appUserDto;

        trainerProfileDto.setUserType("Trainer");
        trainerProfileDto.setClientProfiles(trainerProfile.getClients());


        return trainerProfileDto;
    }

    private AppUserDto clientToDto(AppUserDto appUserDto, ClientProfile clientProfile){
        ClientProfileDto clientProfileDto = (ClientProfileDto) appUserDto;

        clientProfileDto.setTrainer(clientProfile.getTrainer());
        clientProfileDto.setGoals(clientProfile.getGoals());

        return clientProfileDto;
    }
}
