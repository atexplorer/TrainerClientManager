package org.atexplorer.TrainerClientManager.controller;

import jakarta.validation.Valid;
import org.atexplorer.TrainerClientManager.dto.CreateAppUserDto;
import org.atexplorer.TrainerClientManager.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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


}
