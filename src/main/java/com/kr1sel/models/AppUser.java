package com.kr1sel.models;


import com.kr1sel.utils.Interest;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class AppUser extends AbstractModel{

    private String name;

    private int age;

    private String location;

    private boolean isActive;

    private Set<Interest> interests;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Meetup> meetups;

    @ManyToMany
    @JoinTable(
            name = "user_connections",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<AppUser> friends;

    public AppUser(String name, int age, String location, Set<Interest> interests) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.interests = interests;
        this.isActive = true;
    }

    public AppUser() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser person = (AppUser) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(location, person.location) && Objects.equals(interests, person.interests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, location, interests);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", location='" + location + '\'' +
                ", interests=" + interests +
                '}';
    }
}
