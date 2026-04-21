package org.atexplorer.TrainerClientManager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAppUserDto extends AppUserDto {

    @NotBlank
    @Size(min = 6)
    private String password;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
