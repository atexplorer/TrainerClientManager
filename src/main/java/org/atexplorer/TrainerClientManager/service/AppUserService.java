package org.atexplorer.TrainerClientManager.service;

import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;

public interface AppUserService {

    void createClientAccount(CreateClientAccountDto request);

    void createTrainerAccount(CreateTrainerAccountDto request);

    void updateAssignedTrainer(String clientUsername, String trainerUsername);

    void addToClientList(String clientUsername, String trainerUsername);
}
