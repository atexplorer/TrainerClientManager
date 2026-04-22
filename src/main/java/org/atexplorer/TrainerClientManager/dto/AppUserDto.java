package org.atexplorer.TrainerClientManager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * This class will should never return the Authority, password, or id of the AppUser.
 * Maybe this should be called Profile DTO, Making it abstract. The profile would still contain a username.
 * Then we can extend this to Trainer and Client profile DTOs, that way other necessary client data can be returned.
 */
public class AppUserDto {
    @NotBlank
    @Size(min = 6)
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    @NotBlank
    @Email
    private String email;
    //This should be kept that way if the client web page needs to display the userType to an admin they have access to this info.
    //I still like the idea of having specific endpoints for creating different users, but it would result in duplicating a lot of code.
    private String userType;
    private boolean isActive;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
