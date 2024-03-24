package com.kr1sel.repositories;

import com.kr1sel.models.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetupRepository extends JpaRepository<Meetup, Long> {
}
