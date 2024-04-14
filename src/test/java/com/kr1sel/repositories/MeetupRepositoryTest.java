package com.kr1sel.repositories;

import com.kr1sel.models.AppUser;
import com.kr1sel.models.Meetup;
import com.kr1sel.utils.Interest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/meetupRepositoryTest/meetupData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class MeetupRepositoryTest {

    @Autowired
    private MeetupRepository underTest;

    @Autowired
    private AppUserRepository appUserRepository;

    Short[] interestsSetOne = new Short[]{0};

    private static LocalDateTime dateTimeNow = LocalDateTime.now();


    @Test
    void shouldNotReturnEventsDueToAuthorId() {
        List<Meetup> result = underTest.findByInterestsAndLocationAndNotPrivate(
                interestsSetOne,
                "Wroclaw",
                dateTimeNow,
                1L);
        assertEquals(result.size(), 0);
    }

    @Test
    void shouldReturnAllPublicEventsFilteredByInterests() {
        List<Meetup> result = underTest.findByInterestsAndLocationAndNotPrivate(
                interestsSetOne,
                "Wroclaw",
                dateTimeNow,
                2L);
        assertEquals(result.size(), 5);
    }

    @Test
    void shouldReturnAllPublicEventsFilteredByInterestsWithPage() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Meetup> result = underTest.findByInterestsAndLocationAndNotPrivate(interestsSetOne, "Wroclaw", dateTimeNow, 2L, pageable);
        assertEquals(result.size(), 2);
        pageable = PageRequest.of(1, 2);
        result = underTest.findByInterestsAndLocationAndNotPrivate(interestsSetOne, "Wroclaw", dateTimeNow, 2L, pageable);
        assertEquals(result.size(), 2);
        pageable = PageRequest.of(2, 2);
        result = underTest.findByInterestsAndLocationAndNotPrivate(interestsSetOne, "Wroclaw", dateTimeNow, 2L, pageable);
        assertEquals(result.size(), 1);
    }

    @Test
    @Disabled
    void shouldFilterRecordsByInterestsAndLocationAndStartTimeAndCreatePage() {
    }
}