package org.atexplorer.TrainerClientManager.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("CLIENT")
public class ClientProfile extends UserProfile {
    private String[] goals;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private TrainerProfile trainer;

    public TrainerProfile getTrainer() {
        return trainer;
    }

    public void setTrainer(TrainerProfile trainer) {
        this.trainer = trainer;
    }

    public String[] getGoals() {
        return goals;
    }

    public void setGoals(String[] goals) {
        this.goals = goals;
    }
}
