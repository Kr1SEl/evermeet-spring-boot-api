package com.kr1sel.repositories;

import com.kr1sel.models.AppUser;
import com.kr1sel.models.AppUserRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserRatingRepository extends JpaRepository<AppUserRating, UserDetails> {
}
