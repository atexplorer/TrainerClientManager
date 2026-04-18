package org.atexplorer.TrainerClientManager.factory;

import org.atexplorer.TrainerClientManager.dto.AppUserDto;
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
        userProfile.setActive(true);
        return userProfile;
    }
}
