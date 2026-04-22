package org.atexplorer.TrainerClientManager.dto;

import org.atexplorer.TrainerClientManager.entity.ClientProfile;

import java.util.List;

public class TrainerProfileDto extends AppUserDto{

    private List<ClientProfile> clientProfiles;

    public List<ClientProfile> getClientProfiles() {
        return clientProfiles;
    }

    public void setClientProfiles(List<ClientProfile> clientProfiles) {
        this.clientProfiles = clientProfiles;
    }
}
