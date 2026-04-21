package org.atexplorer.TrainerClientManager.factory;

import org.atexplorer.TrainerClientManager.dto.AppUserDto;
import org.atexplorer.TrainerClientManager.entity.UserProfile;

public class ProfileMapperFactory {

    public AppUserDto mapProfileToDto(UserProfile userProfile){

        return switch (userProfile){
            case UserProfile u -> new AppUserDto();
        };
    }
}
