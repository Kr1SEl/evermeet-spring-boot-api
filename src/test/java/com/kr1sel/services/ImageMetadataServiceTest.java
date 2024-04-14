package com.kr1sel.services;

import com.kr1sel.exceptions.UserAlreadyExistsException;
import com.kr1sel.models.AppUser;
import com.kr1sel.repositories.AppUserRepository;
import com.kr1sel.utils.Interest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageMetadataServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private AppUserService underTest;

    String username = "KyryloTest";
    AppUser user = new AppUser(
            "Kyrylo",
            username,
            "password",
            (short) 22,
            "Wroclaw",
            Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING));

    @Test
    void loadUserByUsernameThrowsException() {
        assertThrows(UsernameNotFoundException.class, () -> underTest.loadUserByUsername(username));
        verify(userRepository).findByUsername(username);
    }

    @Test
    void shouldLoadUserByUsername() {
        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));
        UserDetails answer = underTest.loadUserByUsername(username);
        assertEquals(answer.getUsername(), username);
        verify(userRepository).findByUsername(username);
        lenient().when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    }

    @Test
    void signUpShouldRegistersUser() throws UserAlreadyExistsException {

        underTest.signUp(user);
        ArgumentCaptor<AppUser> appUserArgumentCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(userRepository).save(appUserArgumentCaptor.capture());
        AppUser captured =  appUserArgumentCaptor.getValue();
        assertEquals(captured, user);
    }

    @Test
    void signUpThrowsException() throws UserAlreadyExistsException {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistsException.class, () -> underTest.signUp(user));
        verify(userRepository, never()).save(user);
        lenient().when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    }
}