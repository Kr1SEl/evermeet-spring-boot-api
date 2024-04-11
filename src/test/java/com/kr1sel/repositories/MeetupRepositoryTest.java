package com.kr1sel.repositories;

import com.kr1sel.models.AppUser;
import com.kr1sel.models.Meetup;
import com.kr1sel.utils.Interest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = {"/sql/meetupData.sql"})
class MeetupRepositoryTest {

    @Autowired
    private MeetupRepository underTest;

    @Test
    void shouldFilterRecordsByInterestsAndLocationAndStartTime() {
    }

    @Test
    void shouldFilterRecordsByInterestsAndLocationAndStartTimeAndCreatePage() {
    }
}