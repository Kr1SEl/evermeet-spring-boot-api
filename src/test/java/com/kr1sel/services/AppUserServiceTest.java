package com.kr1sel.services;

import com.kr1sel.exceptions.UserAlreadyExistsException;
import com.kr1sel.exceptions.UserNotFoundException;
import com.kr1sel.mappers.AppUserMapper;
import com.kr1sel.models.AppUser;
import com.kr1sel.repositories.AppUserRepository;
import com.kr1sel.utils.Interest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AppUserService underTest;

    @Test
    void loadUserByUsernameThrowsException() {
        String username = "KyryloTest";
        assertThrows(UsernameNotFoundException.class, () -> underTest.loadUserByUsername(username));
        verify(userRepository).findByUsername(username);
    }

    @Test
    void loadUserByUsername() {
        String username = "KyryloTest";
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(new AppUser(
                        "Kyrylo",
                        username,
                        "password",
                        (short) 21,
                        "Wroclaw",
                        Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING))));
        UserDetails answer = underTest.loadUserByUsername(username);
        assertEquals(answer.getUsername(), username);
        verify(userRepository).findByUsername(username);
        lenient().when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    }

    @Test
    void signUpRegistersUser() throws UserAlreadyExistsException {
        String username = "KyryloTest";
        AppUser user = new AppUser(
                "Kyrylo",
                username,
                "password",
                (short) 22,
                "Wroclaw",
                Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING));
        underTest.signUp(user);
        ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(appUserArgumentCaptor.capture());
        AppUser captured =  appUserArgumentCaptor.getValue();
        assertEquals(captured, user);
    }

    @Test
    void signUpThrowsException() throws UserAlreadyExistsException {
        String username = "KyryloTest";
        AppUser user = new AppUser(
                "Kyrylo",
                username,
                "password",
                (short) 22,
                "Wroclaw",
                Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> underTest.signUp(user));
        verify(userRepository, never()).save(user);
        lenient().when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    }
}