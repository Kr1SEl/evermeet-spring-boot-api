package com.kr1sel.services;

import com.kr1sel.dto.NewAppUserRequestDTO;
import com.kr1sel.exceptions.UserAlreadyExistsException;
import com.kr1sel.models.AppUser;
import com.kr1sel.models.AppUserRating;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final AppUserService appUserService;
    private final AppUserRatingService appUserRatingService;

    @Autowired
    public RegistrationService(AppUserService appUserService,
                               AppUserRatingService appUserRatingService){
        this.appUserService = appUserService;
        this.appUserRatingService = appUserRatingService;
    }

    @Transactional
    public void register(NewAppUserRequestDTO request)
            throws UserAlreadyExistsException {
        AppUser newUser = new AppUser(
                request.name(),
                request.username(),
                request.password(),
                request.age(),
                request.location(),
                request.interests()
        );
        appUserService.signUp(newUser);
        appUserRatingService.createUserRating(newUser);
    }

}
