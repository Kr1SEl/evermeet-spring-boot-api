package com.kr1sel.models;

import com.kr1sel.utils.Interest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Meetup extends AbstractModel{

    @Column(nullable = false, length = 50)
    private String eventName;

    private String location;

//    TODO Azure BLOB for image

    private boolean isPrivate;

    private int maxPeople;

    private Set<Interest> interests;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    @Column(nullable = false )
    private LocalDateTime startDateTime;

    @OneToMany
    @JoinTable(
            name = "meetup_attendees",
            joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<AppUser> attendees;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AppUser author;

    public Meetup(String eventName,
                  String location,
                  boolean isPrivate,
                  int maxPeople,
                  AppUser author,
                  Set<Interest> interests,
                  LocalDateTime createdDateTime,
                  LocalDateTime startDateTime) {
        this.eventName = eventName;
        this.location = location;
        this.isPrivate = isPrivate;
        this.maxPeople = maxPeople;
        this.author = author;
        this.interests = interests;
        this.createdDateTime = createdDateTime;
        this.startDateTime = startDateTime;
    }

    public boolean addAttendee(AppUser user){
        return this.attendees.add(user);
    }

    public void removeAttendee(AppUser user){
        this.attendees.remove(user);
    }

}
