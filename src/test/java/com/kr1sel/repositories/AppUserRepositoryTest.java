package com.kr1sel.repositories;

import com.kr1sel.models.AppUser;
import com.kr1sel.utils.Interest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindUserByUsername() {
        String username = "KyryloAdmin";
        AppUser user = new AppUser("Kyrylo",
                username,
                "password",
                (short) 22,
                "Wroclaw",
                Set.of(Interest.IT, Interest.BIKES, Interest.CARS, Interest.GAMING));
        underTest.save(user);
        Optional<AppUser> foundUser = underTest.findByUsername(username);
        assertTrue(foundUser.isPresent());
    }

    @Test
    void shouldNotFindUserByUsername() {
        String username = "KyryloAdmin";
        Optional<AppUser> foundUser = underTest.findByUsername(username);
        assertFalse(foundUser.isPresent());
    }
}