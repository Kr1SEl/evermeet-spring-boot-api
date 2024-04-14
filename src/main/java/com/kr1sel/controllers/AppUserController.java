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

    @PutMapping(path = "send-friend-request/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendFriendshipRequest(@PathVariable String username, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            FriendshipAlreadyEstablishedException,
            RequestAlreadySentException,
            FriendshipToUserIsNotAllowedException {
        appUserService.sendFriendshipRequest(username, user);
    }

    @PutMapping(path = "remove-friend-request/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFriendshipRequest(@PathVariable String username, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            NothingToModifyException {
        appUserService.removeFriendshipRequest(username, user);
    }

    @PutMapping(path = "accept-friend-request/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void acceptFriendshipRequest(@PathVariable String username, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            NoFriendshipRequestException {
        appUserService.acceptFriendshipRequest(username, user);
    }

    @PutMapping(path = "remove-friend/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFriend(@PathVariable String username, @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            NothingToModifyException {
        appUserService.removeFriend(username, user);
    }
}
