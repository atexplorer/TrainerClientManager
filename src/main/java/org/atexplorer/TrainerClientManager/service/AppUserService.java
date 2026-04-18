package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.dto.CreateAppUserDto;

public interface AppUserService {

    void addUser(CreateAppUserDto request);
}
