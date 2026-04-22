package org.atexplorer.TrainerClientManager.dto;

import org.atexplorer.TrainerClientManager.entity.TrainerProfile;

public class ClientProfileDto extends AppUserDto{
    private String[] goals;
    private TrainerProfile trainer;

    public String[] getGoals() {
        return goals;
    }

    public void setGoals(String[] goals) {
        this.goals = goals;
    }

    public TrainerProfile getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerProfile trainer) {
        this.trainer = trainer;
    }
}
