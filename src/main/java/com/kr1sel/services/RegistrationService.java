package com.kr1sel.services;

import com.kr1sel.dto.NewAppUserRequestDTO;
import com.kr1sel.exceptions.UserAlreadyExistsException;
import com.kr1sel.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final AppUserService appUserService;

    @Autowired
    public RegistrationService(AppUserService appUserService){
        this.appUserService = appUserService;
    }

    public boolean register(NewAppUserRequestDTO request) throws UserAlreadyExistsException {
        return appUserService.signUp(
                new AppUser(
                    request.name(),
                    request.username(),
                    request.password(),
                    request.age(),
                    request.location())
        );
    }

}
