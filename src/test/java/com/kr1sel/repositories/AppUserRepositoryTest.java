package com.kr1sel.repositories;

import com.kr1sel.models.AppUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindUserByUsername() {
        String username = "KyryloAdmin";
        AppUser user = new AppUser("Kyrylo", username, "password", 22, "Wroclaw");
        underTest.save(user);
        Optional<AppUser> foundUser = underTest.findByUsername(username);
        assertTrue(foundUser.isPresent());
    }

    @Test
    void itShouldNotFindUserByUsername() {
        String username = "KyryloAdmin";
        Optional<AppUser> foundUser = underTest.findByUsername(username);
        assertFalse(foundUser.isPresent());
    }
}