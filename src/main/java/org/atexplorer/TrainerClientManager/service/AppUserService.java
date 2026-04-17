package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.dto.CreateAppUserRequest;

public interface AppUserService {

    void addUser(CreateAppUserRequest request);
}
