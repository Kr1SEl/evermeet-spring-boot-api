package com.kr1sel.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Meetup extends AbstractModel{

    private String eventName;

    private String location;

//    TODO Azure BLOB

    private boolean isPrivate;

    private int maxPeople;

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

    public Meetup(String eventName, String location, boolean isPrivate, int maxPeople, AppUser author) {
        this.eventName = eventName;
        this.location = location;
        this.isPrivate = isPrivate;
        this.maxPeople = maxPeople;
        this.author = author;
    }
}
