package com.kr1sel.repositories;

import com.kr1sel.models.AppUser;
import com.kr1sel.models.Meetup;
import com.kr1sel.utils.Interest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {

    String FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_NOT_PRIVATE =
            "SELECT DISTINCT * FROM Meetup m " +
                    "WHERE m.interests && :interest " +
                    "AND m.is_private = false " +
                    "AND m.location LIKE %:location% " +
                    "AND m.start_date_time > :startDateTime " +
                    "AND m.author_id <> :authorId";

    String FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_AUTHOR_IN =
            "SELECT DISTINCT m FROM Meetup m " +
                    "WHERE m.interests IN :interests " +
                    "AND m.isPrivate = true " +
                    "AND m.location LIKE %:location% " +
                    "AND m.startDateTime > :startDateTime " +
                    "AND m.author IN :authors";

    @Query(value = FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_NOT_PRIVATE, nativeQuery = true)
    List<Meetup> findByInterestsAndLocationAndNotPrivate(
            @Param("interest") Short[] interest,
            @Param("location") String location,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("authorId") Long authorId);

    @Query(value = FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_NOT_PRIVATE, nativeQuery = true)
    List<Meetup> findByInterestsAndLocationAndNotPrivate(
            @Param("interest") Short[] interest,
            @Param("location") String location,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("authorId") Long authorId,
            Pageable pageable);

    @Query(FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_AUTHOR_IN)
    List<Meetup> findByInterestsAndLocationAndAuthorInSet(
            @Param("interests") Set<Interest> interests,
            @Param("location") String location,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("authors") Set<AppUser> authors);

    @Query(FILTER_MEETUPS_BY_INTERESTS_AND_LOCATION_AND_AUTHOR_IN)
    List<Meetup> findByInterestsAndLocationAndAuthorInSet(
            @Param("interests") Set<Interest> interests,
            @Param("location") String location,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("authors") Set<AppUser> authors,
            Pageable pageable);
}
