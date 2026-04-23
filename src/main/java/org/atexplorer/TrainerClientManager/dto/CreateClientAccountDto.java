package org.atexplorer.TrainerClientManager.dto;

public class CreateClientAccountDto extends CreateAccountDto {

    private String[] goals;

    public String[] getGoals() {
        return goals;
    }

    public void setGoals(String[] goals) {
        this.goals = goals;
    }
}
