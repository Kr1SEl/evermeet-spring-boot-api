package com.kr1sel.controllers;

import com.kr1sel.dto.AppUserDTO;
import com.kr1sel.mappers.AppUserMapper;
import com.kr1sel.models.AppUser;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/user")
public class AppUserController {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Autowired
    public AppUserController(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
        this.appUserMapper = new AppUserMapper();
    }

    @GetMapping(path = "{userId}")
    public AppUserDTO getUser(@PathVariable Integer userId) throws UserNotFoundException {
        Optional<AppUserDTO> user =  appUserRepository.findById(userId).map(appUserMapper);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserNotFoundException();
        }
    }

    @GetMapping
    public List<AppUserDTO> findAllUsers(){
        return appUserRepository.findAll().stream().map(appUserMapper).toList();
    }

    @DeleteMapping(path = "{userId}")
    public AppUserDTO deleteUser(@PathVariable Integer userId) throws UserNotFoundException {
        Optional<AppUser> deleted = appUserRepository.findById(userId);
        if(deleted.isPresent()){
            appUserRepository.deleteById(userId);
            return deleted.map(appUserMapper).get();
        }else{
            throw new UserNotFoundException();
        }
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable Integer userId,@RequestBody AppUser appUser) throws UserNotFoundException {
        Optional<AppUser> toChange = appUserRepository.findById(userId);
        if(toChange.isPresent()){
            AppUser userToChange = toChange.get();
            userToChange.setLocation(appUser.getLocation());
            userToChange.setUsername(appUser.getUsername());
            userToChange.setAge(appUser.getAge());
            userToChange.setInterests(appUser.getInterests());
            appUserRepository.save(userToChange);
        }else{
            throw new UserNotFoundException();
        }
    }

}
