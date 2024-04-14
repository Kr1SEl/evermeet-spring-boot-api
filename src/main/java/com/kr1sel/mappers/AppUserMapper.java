package com.kr1sel.mappers;

import com.kr1sel.dto.AppUserDTO;
import com.kr1sel.models.AppUser;

import java.util.function.Function;

public class AppUserMapper implements Function<AppUser, AppUserDTO> {
    @Override
    public AppUserDTO apply(AppUser appUser) {
        return new AppUserDTO(
                appUser.getUsername(),
                appUser.getName(),
                appUser.getAge(),
                appUser.getLocation(),
                appUser.getInterests()
        );
    }
}
