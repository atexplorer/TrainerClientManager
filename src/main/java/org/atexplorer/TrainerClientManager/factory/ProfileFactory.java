package org.atexplorer.TrainerClientManager.factory;

import org.atexplorer.TrainerClientManager.dto.AppUserDto;
import org.atexplorer.TrainerClientManager.dto.ClientProfileDto;
import org.atexplorer.TrainerClientManager.dto.TrainerProfileDto;
import org.atexplorer.TrainerClientManager.entity.ClientProfile;
import org.atexplorer.TrainerClientManager.entity.TrainerProfile;
import org.atexplorer.TrainerClientManager.entity.UserProfile;
import org.atexplorer.TrainerClientManager.entity.UserType;

public class ProfileFactory {

    public static UserProfile createUserProfile(AppUserDto appUserDto){
        UserProfile userProfile = switch (UserType.valueOf(appUserDto.getUserType())){
            case CLIENT -> new ClientProfile();
            case TRAINER -> new TrainerProfile();
        };
        userProfile.setEmail(appUserDto.getEmail());
        userProfile.setName(appUserDto.getFirstName() + " " + appUserDto.getLastName());
        userProfile.setFirstName(appUserDto.getFirstName());
        userProfile.setMiddleName(appUserDto.getMiddleName());
        userProfile.setLastName(appUserDto.getLastName());
        userProfile.setSuffix(appUserDto.getSuffix());
        userProfile.setActive(true);
        return userProfile;
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
