package com.kr1sel.controllers;

import com.kr1sel.dto.AppUserDTO;
import com.kr1sel.exceptions.*;
import com.kr1sel.models.AppUser;
import com.kr1sel.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/user")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    @GetMapping(path = "{id}")
    public AppUserDTO getUserDetails(@PathVariable Long id)
            throws UserNotFoundException {
        return appUserService.getUserByUserId(id);
    }

    @DeleteMapping
    public AppUserDTO deleteAccount(@AuthenticationPrincipal AppUser user)
            throws UserNotFoundException {
        return appUserService.deleteUserByObject(user);
    }

    @PutMapping(path = "send-friend-request/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendFriendshipRequest(@PathVariable Long id, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            FriendshipAlreadyEstablishedException,
            RequestAlreadySentException,
            FriendshipToUserIsNotAllowedException {
        appUserService.sendFriendshipRequest(id, user);
    }

    @PutMapping(path = "remove-friend-request/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFriendshipRequest(@PathVariable Long id, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            NothingToModifyException {
        appUserService.removeFriendshipRequest(id, user);
    }

    @PutMapping(path = "accept-friend-request/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptFriendshipRequest(@PathVariable Long id, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            NoFriendshipRequestException {
        appUserService.acceptFriendshipRequest(id, user);
    }

    @PutMapping(path = "remove-friend/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFriend(@PathVariable Long id, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            NothingToModifyException {
        appUserService.removeFriend(id, user);
    }
}
