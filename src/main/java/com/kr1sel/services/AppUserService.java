package com.kr1sel.services;

import com.kr1sel.exceptions.UserAlreadyExistsException;
import com.kr1sel.models.AppUser;
import com.kr1sel.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username '" + username +  "' does not exist"));
    }

    public boolean signUp(AppUser appUser) throws UserAlreadyExistsException {
        boolean userAlreadyExists = appUserRepository.findByUsername(appUser.getUsername()).isPresent();
        if(userAlreadyExists){
            throw new UserAlreadyExistsException("User with username '" + appUser.getUsername() +  "' already exist");
        }else{
            String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
            appUser.setPassword(encodedPassword);
            appUserRepository.save(appUser);
            return true;
        }
    }
}
