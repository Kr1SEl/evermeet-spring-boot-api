package com.kr1sel.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

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

    public Meetup() {
    }

    public Meetup(String eventName, String location, boolean isPrivate, int maxPeople, AppUser author) {
        this.eventName = eventName;
        this.location = location;
        this.isPrivate = isPrivate;
        this.maxPeople = maxPeople;
        this.author = author;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meetup meetup = (Meetup) o;
        return isPrivate == meetup.isPrivate && maxPeople == meetup.maxPeople && Objects.equals(eventName, meetup.eventName) && Objects.equals(location, meetup.location) && Objects.equals(author, meetup.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, location, isPrivate, maxPeople, author);
    }

    @Override
    public String toString() {
        return "Meetup{" +
                "eventName='" + eventName + '\'' +
                ", location='" + location + '\'' +
                ", isPrivate=" + isPrivate +
                ", maxPeople=" + maxPeople +
                ", author=" + author +
                '}';
    }
}
