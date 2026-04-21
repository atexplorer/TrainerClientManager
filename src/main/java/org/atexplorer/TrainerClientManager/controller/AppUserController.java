package org.atexplorer.TrainerClientManager.controller;

import jakarta.validation.Valid;
import org.atexplorer.TrainerClientManager.dto.CreateAppUserDto;
import org.atexplorer.TrainerClientManager.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("api/accounts")
    public void addNewUser(@RequestBody @Valid CreateAppUserDto request){
        appUserService.addUser(request);
    }


    @PutMapping("api/accounts/client")
    public void updateAssignedTrainer(@RequestParam(required = true) String trainerUsername, @AuthenticationPrincipal UserDetails userDetails){
        String clientUsername = userDetails.getUsername();
    }


}
