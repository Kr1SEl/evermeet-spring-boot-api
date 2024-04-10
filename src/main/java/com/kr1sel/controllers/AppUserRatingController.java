package com.kr1sel.controllers;

import com.kr1sel.exceptions.UserIsOwnerOfTheRatingException;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.exceptions.UserRatingNotFoundException;
import com.kr1sel.exceptions.UserRatingOutOfBoundsException;
import com.kr1sel.models.AppUser;
import com.kr1sel.services.AppUserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/rating")
public class AppUserRatingController {

    private final AppUserRatingService appUserRatingService;

    @Autowired
    public AppUserRatingController(AppUserRatingService appUserRatingService){
        this.appUserRatingService = appUserRatingService;
    }

    @GetMapping(path = "{username}")
    public double getRatingByUsername(@PathVariable String username)
            throws UserRatingNotFoundException, UserNotFoundException {
        return appUserRatingService.getRatingByUsername(username);
    }

    @PutMapping(path = "{username}")
    public void rateUserByUsername(@PathVariable String username,
                                   @RequestParam byte grade,
                                   @AuthenticationPrincipal AppUser user)
            throws UserNotFoundException,
            UserRatingOutOfBoundsException,
            UserRatingNotFoundException,
            UserIsOwnerOfTheRatingException{
        appUserRatingService.addRatingByUsername(username, grade, user);
    }
}
