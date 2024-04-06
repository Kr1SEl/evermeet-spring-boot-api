package com.kr1sel.repositories;

import com.kr1sel.models.Meetup;
import com.kr1sel.utils.Interest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {

    String FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_START_DATE_TIME =
            "SELECT DISTINCT m FROM Meetup m " +
            "JOIN m.interests i " +
            "WHERE i IN :interests " +
            "AND m.location LIKE %:location% " +
            "AND m.startDateTime > :startDateTime";

    @Query(FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_START_DATE_TIME)
    List<Meetup> findByInterestsAndLocationAndStartDateTimeGreaterThan(
            @Param("interests") Set<Interest> interests,
            @Param("location") String location,
            @Param("startDateTime") LocalDateTime startDateTime);

    @Query(FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_START_DATE_TIME)
    List<Meetup> findByInterestsAndLocationAndStartDateTimeGreaterThan(
            @Param("interests") Set<Interest> interests,
            @Param("location") String location,
            @Param("startDateTime") LocalDateTime startDateTime,
            Pageable pageable);
}
