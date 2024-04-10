package com.kr1sel.services;

import com.kr1sel.dto.AppUserDTO;
import com.kr1sel.exceptions.UserAlreadyExistsException;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.mappers.AppUserMapper;
import com.kr1sel.models.AppUser;
import com.kr1sel.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserMapper appUserMapper;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appUserMapper = new AppUserMapper();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username '" + username +  "' does not exist"));
    }

    public AppUserDTO getUserByUserId(Long id) throws UserNotFoundException {
        Optional<AppUserDTO> user =  appUserRepository.findById(id).map(appUserMapper);
        if(user.isPresent()){
            return user.get();
        }else{
            throw new UserNotFoundException();
        }
    }

    public AppUserDTO deleteUserByUserId(Long id) throws UserNotFoundException {
        Optional<AppUser> deleted = appUserRepository.findById(id);
        if(deleted.isPresent()){
            appUserRepository.deleteById(id);
            Optional<AppUserDTO> deletedDto = deleted.map(appUserMapper);
            if(deletedDto.isPresent()){
                return deletedDto.get();
            }else{
                throw new UserNotFoundException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    public List<AppUserDTO> getAllUsers(){
        return appUserRepository.findAll().stream().map(appUserMapper).toList();
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

    public void updateUser(AppUser user){
        appUserRepository.save(user);
    }
}
