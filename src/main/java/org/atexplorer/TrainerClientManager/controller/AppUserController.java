package org.atexplorer.TrainerClientManager.controller;

import jakarta.validation.Valid;
import org.atexplorer.TrainerClientManager.dto.CreateClientAccountDto;
import org.atexplorer.TrainerClientManager.dto.CreateTrainerAccountDto;
import org.atexplorer.TrainerClientManager.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("api/accounts/client")
    public void createClientAccount(@RequestBody @Valid CreateClientAccountDto request) {
        appUserService.createClientAccount(request);
    }

    @PostMapping("api/accounts/trainer")
    public void createTrainerAccount(@RequestBody @Valid CreateTrainerAccountDto request) {
        appUserService.createTrainerAccount(request);
    }

    @PutMapping("api/accounts/client")
    public void updateAssignedTrainer(@RequestParam(required = true) String trainerUsername, @AuthenticationPrincipal UserDetails userDetails) {
        String clientUsername = userDetails.getUsername();
    }
}
