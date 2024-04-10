package com.kr1sel.controllers;

import com.kr1sel.dto.AppUserDTO;
import com.kr1sel.mappers.AppUserMapper;
import com.kr1sel.models.AppUser;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.repositories.AppUserRepository;
import com.kr1sel.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "admin/api/v1/user")
public class AdminAppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AdminAppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @GetMapping(path = "{userId}")
    public AppUserDTO getUser(@PathVariable Long userId)
            throws UserNotFoundException {
        return appUserService.getUserByUserId(userId);
    }

    @GetMapping
    public List<AppUserDTO> findAllUsers(){
        return appUserService.getAllUsers();
    }

    @DeleteMapping(path = "{userId}")
    public AppUserDTO deleteUser(@PathVariable Long userId)
            throws UserNotFoundException {
        return appUserService.deleteUserByUserId(userId);
    }

}
