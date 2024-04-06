package com.kr1sel.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class AppUserRating{

    @Id
    private Long userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser ratingOwner;

    private int numberOfVotes;

    private long totalRating;

    public AppUserRating(AppUser ratingOwner, int numberOfVotes, long totalRating) {
        this.ratingOwner = ratingOwner;
        this.numberOfVotes = numberOfVotes;
        this.totalRating = totalRating;
    }

    public void addRating(byte grade){
        totalRating += grade;
        numberOfVotes++;
    }
}
