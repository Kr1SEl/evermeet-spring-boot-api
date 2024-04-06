package com.kr1sel.services;

import com.kr1sel.exceptions.UserIsOwnerOfTheRatingException;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.exceptions.UserRatingOutOfBoundsException;
import com.kr1sel.exceptions.UserRatingNotFoundException;
import com.kr1sel.models.AppUser;
import com.kr1sel.models.AppUserRating;
import com.kr1sel.repositories.AppUserRatingRepository;
import com.kr1sel.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserRatingService {

    private final AppUserRatingRepository appUserRatingRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserRatingService(AppUserRatingRepository appUserRatingRepository,
                                AppUserRepository appUserRepository){
        this.appUserRatingRepository = appUserRatingRepository;
        this.appUserRepository = appUserRepository;
    }

    public void createUserRating(AppUser user){
        appUserRatingRepository.save(new AppUserRating(
                user,
                0,
                0));
    }

    public double getRatingByUsername(String username)
            throws UserRatingNotFoundException, UserNotFoundException {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if(user.isPresent()) {
            Optional<AppUserRating> userRating = appUserRatingRepository.findById(user.get());
            if (userRating.isPresent()) {
                return calculateRating(userRating.get());
            } else {
                throw new UserRatingNotFoundException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    public void addRatingByUsername(String username, byte grade, AppUser requestUser)
            throws UserNotFoundException,
            UserRatingNotFoundException,
            UserRatingOutOfBoundsException,
            UserIsOwnerOfTheRatingException{
        if(grade < 1 || grade > 5){
            throw new UserRatingOutOfBoundsException();
        }
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        if(user.isPresent()) {
            if (requestUser.equals(user)) {
                throw new UserIsOwnerOfTheRatingException();
            }
            Optional<AppUserRating> userRating = appUserRatingRepository.findById(user.get());
            if (userRating.isPresent()) {
                AppUserRating userRatingReference = userRating.get();
                userRatingReference.addRating(grade);
                appUserRatingRepository.save(userRatingReference);
            } else {
                throw new UserRatingNotFoundException();
            }
        }else{
            throw new UserNotFoundException();
        }
    }

    private double calculateRating(AppUserRating userRating){
        return (double) userRating.getTotalRating() / userRating.getNumberOfVotes();
    }
}
