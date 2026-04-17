package org.atexplorer.TrainerClientManager.controller;

import jakarta.validation.Valid;
import org.atexplorer.TrainerClientManager.dto.CreateAppUserRequest;
import org.atexplorer.TrainerClientManager.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("api/accounts")
    public void addTrainer(@RequestBody @Valid CreateAppUserRequest request){
        appUserService.addUser(request);
    }


}
