package org.atexplorer.TrainerClientManager.dto;

public class CreateTrainerAccountDto extends CreateAccountDto {

    private String[] certifications;

    public String[] getCertifications() {
        return certifications;
    }

    public void setCertifications(String[] certifications) {
        this.certifications = certifications;
    }
}
