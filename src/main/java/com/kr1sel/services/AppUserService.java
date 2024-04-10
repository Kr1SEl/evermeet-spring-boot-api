package com.kr1sel.services;

import com.kr1sel.dto.AppUserDTO;
import com.kr1sel.exceptions.*;
import com.kr1sel.mappers.AppUserMapper;
import com.kr1sel.models.AppUser;
import com.kr1sel.models.ImageMetadata;
import com.kr1sel.repositories.AppUserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public AppUserDTO deleteUserByObject(AppUser user) throws UserNotFoundException {
        return deleteUserByUserId(user.getId());
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

    @Transactional
    public void sendFriendshipRequest(Long targetUserId, AppUser senderModel)
            throws UserNotFoundException,
            FriendshipAlreadyEstablishedException,
            RequestAlreadySentException,
            FriendshipToUserIsNotAllowedException {
        Optional<AppUser> target = appUserRepository.findById(targetUserId);
        Optional<AppUser> sender = appUserRepository.findById(senderModel.getId());
        if(target.isPresent() && sender.isPresent()){
            AppUser targetUser = target.get();
            AppUser senderUser = sender.get();
            if(senderUser.equals(targetUser)){
                throw new FriendshipToUserIsNotAllowedException();
            }
            if(!senderUser.hasFriend(targetUser)){
                if(senderUser.hasFriendRequestsFrom(targetUser)){
                    senderUser.becomeFriendsWith(targetUser);
                    updateUser(senderUser);
                    targetUser.becomeFriendsWith(senderUser);
                    updateUser(targetUser);
                }else if(targetUser.hasFriendRequestsFrom(senderUser)){
                    throw new RequestAlreadySentException();
                }else{
                    targetUser.addFriendRequestFrom(senderUser);
                    updateUser(targetUser);
                }
            }else{
                throw new FriendshipAlreadyEstablishedException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public void removeFriendshipRequest(Long targetUserId, AppUser senderUser)
            throws UserNotFoundException,
            NothingToModifyException {
        Optional<AppUser> target = appUserRepository.findById(targetUserId);
        if(target.isPresent()){
            AppUser targetUser = target.get();
            if(targetUser.hasFriendRequestsFrom(senderUser)){
                targetUser.removeFriendRequestFrom(senderUser);
                updateUser(targetUser);
            }else{
                throw new NothingToModifyException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public void acceptFriendshipRequest(Long targetUserId, AppUser senderModel)
            throws UserNotFoundException,
            NoFriendshipRequestException {
        Optional<AppUser> target = appUserRepository.findById(targetUserId);
        Optional<AppUser> sender = appUserRepository.findById(senderModel.getId());
        if(target.isPresent() && sender.isPresent()){
            AppUser targetUser = target.get();
            AppUser senderUser = sender.get();
            if(senderUser.hasFriendRequestsFrom(targetUser)){
                senderUser.becomeFriendsWith(targetUser);
                updateUser(senderUser);
                targetUser.becomeFriendsWith(senderUser);
                updateUser(targetUser);
            }else{
                throw new NoFriendshipRequestException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public void removeFriend(Long targetUserId, AppUser senderModel)
            throws UserNotFoundException,
            NothingToModifyException {
        Optional<AppUser> target = appUserRepository.findById(targetUserId);
        Optional<AppUser> sender = appUserRepository.findById(senderModel.getId());
        if(target.isPresent() && sender.isPresent()){
            AppUser targetUser = target.get();
            AppUser senderUser = sender.get();
            if(senderUser.hasFriend(targetUser)){
                senderUser.removeFriend(targetUser);
                updateUser(senderUser);
                targetUser.removeFriend(senderUser);
                updateUser(targetUser);
            }else{
                throw new NothingToModifyException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    public void updateUserImage(ImageMetadata image, AppUser user){
        user.setUserImage(image);
        updateUser(user);
    }
}
