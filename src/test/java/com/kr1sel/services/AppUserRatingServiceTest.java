package com.kr1sel.services;

import com.kr1sel.exceptions.UserIsOwnerOfTheRatingException;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.exceptions.UserRatingNotFoundException;
import com.kr1sel.exceptions.UserRatingOutOfBoundsException;
import com.kr1sel.models.AppUser;
import com.kr1sel.models.AppUserRating;
import com.kr1sel.repositories.AppUserRatingRepository;
import com.kr1sel.repositories.AppUserRepository;
import com.kr1sel.utils.Interest;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserRatingServiceTest {

    @Mock
    private AppUserRatingRepository appUserRatingRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserRatingService underTest;

    String username = "KyryloTest";
    AppUser user = new AppUser(
            "Kyrylo",
            username,
            "password",
            (short) 22,
            "Wroclaw",
            Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING));
    AppUserRating userRating = new AppUserRating(user, 0, 0);

    @Test
    void createUserRating() {
        underTest.createUserRating(user);
        AppUserRating newRating = new AppUserRating(user, 0, 0);
        verify(appUserRatingRepository).save(any(AppUserRating.class));
    }

    @Test
    void getRatingByUsernameThrowsUserNotFoundException() {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> underTest.getRatingByUsername(username));
        verify(appUserRepository).findByUsername(username);
    }

    @Test
    void getRatingByUsernameThrowsUserRatingNotFoundException() {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(appUserRatingRepository.findById(user)).thenReturn(Optional.empty());
        assertThrows(UserRatingNotFoundException.class, () -> underTest.getRatingByUsername(username));
        verify(appUserRepository).findByUsername(username);
        verify(appUserRatingRepository).findById(user);
    }

    @Test
    void getRatingByUsernameReturnsZeroRating() throws UserNotFoundException, UserRatingNotFoundException {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(appUserRatingRepository.findById(user)).thenReturn(Optional.of(userRating));
        double userRatingValue = underTest.getRatingByUsername(username);
        assertEquals(userRatingValue, 0);
        verify(appUserRepository).findByUsername(username);
        verify(appUserRatingRepository).findById(user);
    }

    @Test
    void getRatingByUsernameReturnsRealRatingTestOne() throws UserNotFoundException, UserRatingNotFoundException {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        AppUserRating specialUserRating = new AppUserRating(user, 13, 62);
        when(appUserRatingRepository.findById(user)).thenReturn(Optional.of(specialUserRating));
        double userRatingValue = underTest.getRatingByUsername(username);
        assertEquals(userRatingValue, 4.8);
        verify(appUserRepository).findByUsername(username);
        verify(appUserRatingRepository).findById(user);
    }

    @Test
    void getRatingByUsernameReturnsRealRatingTestTwo() throws UserNotFoundException, UserRatingNotFoundException {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        AppUserRating specialUserRating = new AppUserRating(user, 7, 18);
        when(appUserRatingRepository.findById(user)).thenReturn(Optional.of(specialUserRating));
        double userRatingValue = underTest.getRatingByUsername(username);
        assertEquals(userRatingValue, 2.6);
        verify(appUserRepository).findByUsername(username);
        verify(appUserRatingRepository).findById(user);
    }

    @Test
    void addRatingByUsernameThrowsUserRatingOutOfBoundExceptionOne() {
        assertThrows(UserRatingOutOfBoundsException.class,
                () -> underTest.addRatingByUsername(username, (byte) 6, user));
    }

    @Test
    void addRatingByUsernameThrowsUserRatingOutOfBoundExceptionTwo() {
        assertThrows(UserRatingOutOfBoundsException.class,
                () -> underTest.addRatingByUsername(username, (byte) 0, user));
    }

    @Test
    void addRatingByUsernameThrowsUserIsOwnerOfTheRatingException() {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        assertThrows(UserIsOwnerOfTheRatingException.class,
                () -> underTest.addRatingByUsername(username, (byte) 3, user));
    }

    @Test
    void addRatingByUsernameThrowsUserNotFoundException() {
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,
                () -> underTest.addRatingByUsername(username, (byte) 3, user));
        verify(appUserRepository).findByUsername(username);
    }

    @Test
    void addRatingByUsernameThrowsUserRatingNotFoundException() {
        String testSpecialUsername = "KyryloTest2";
        AppUser testSpecialUser = new AppUser(
                "Kyrylo",
                testSpecialUsername,
                "password",
                (short) 22,
                "Wroclaw",
                Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING));
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(appUserRatingRepository.findById(user)).thenReturn(Optional.empty());
        assertThrows(UserRatingNotFoundException.class,
                () -> underTest.addRatingByUsername(username, (byte) 3, testSpecialUser));
        verify(appUserRepository).findByUsername(username);
        verify(appUserRatingRepository).findById(user);
    }

    @Test
    void addRatingByUsernameUpdatesUserRating()
            throws UserNotFoundException,
            UserRatingOutOfBoundsException,
            UserIsOwnerOfTheRatingException,
            UserRatingNotFoundException {
        String testSpecialUsername = "KyryloTest2";
        byte grade = 3;
        AppUser testSpecialUser = new AppUser(
                "Kyrylo",
                testSpecialUsername,
                "password",
                (short) 22,
                "Wroclaw",
                Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING));
        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(appUserRatingRepository.findById(user)).thenReturn(Optional.of(userRating));
        underTest.addRatingByUsername(username, grade, testSpecialUser);
        verify(appUserRepository).findByUsername(username);
        verify(appUserRatingRepository).findById(user);
        verify(appUserRatingRepository).save(any(AppUserRating.class));
    }

}