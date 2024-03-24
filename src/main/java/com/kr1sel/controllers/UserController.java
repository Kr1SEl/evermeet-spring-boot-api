package com.kr1sel.controllers;

import com.kr1sel.dto.AppUserDTO;
import com.kr1sel.mappers.AppUserMapper;
import com.kr1sel.models.AppUser;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
        this.appUserMapper = new AppUserMapper();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "OK")
    public void createUser(@RequestBody AppUserDTO appUser){
        userRepository.save(new AppUser(appUser.name(), appUser.age(), appUser.location(), appUser.interests()));
    }

    @GetMapping(path = "{userId}")
    public AppUserDTO getUser(@PathVariable Integer userId) throws UserNotFoundException {
        Optional<AppUserDTO> user =  userRepository.findById(userId).map(appUserMapper);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserNotFoundException();
        }
    }

    @GetMapping
    public List<AppUserDTO> findAllUsers(){
        return userRepository.findAll().stream().map(appUserMapper).toList();
    }

    @DeleteMapping(path = "{userId}")
    public AppUserDTO deleteUser(@PathVariable Integer userId) throws UserNotFoundException {
        Optional<AppUser> deleted = userRepository.findById(userId);
        if(deleted.isPresent()){
            userRepository.deleteById(userId);
            return deleted.map(appUserMapper).get();
        }else{
            throw new UserNotFoundException();
        }
    }

    @PutMapping(path = "{userId}")
    public void updateUser(@PathVariable Integer userId,@RequestBody AppUser appUser) throws UserNotFoundException {
        Optional<AppUser> toChange = userRepository.findById(userId);
        if(toChange.isPresent()){
            AppUser userToChange = toChange.get();
            userToChange.setLocation(appUser.getLocation());
            userToChange.setName(appUser.getName());
            userToChange.setAge(appUser.getAge());
            userToChange.setInterests(appUser.getInterests());
            userRepository.save(userToChange);
        }else{
            throw new UserNotFoundException();
        }
    }

}
