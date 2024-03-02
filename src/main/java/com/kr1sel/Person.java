package com.kr1sel;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

enum Interest {
    PROGRAMMING,
    GAMING,
    SMOKING,
    DRINKING
}

@Entity
public class Person {

    @Id
    @SequenceGenerator(name="person_id_sequence", sequenceName = "person_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_sequence")
    private Integer id;
    private String name;
    private int age;
    private String location;
    private List<Interest> interests;

    public Person(String name, int age, String location, List<Interest> interests) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.interests = interests;
    }

    public Person() {
    }

    public Integer getId() {
        return id;
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

    public void setInterests(List<Interest> interests) {
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

    public List<Interest> getInterests() {
        return interests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
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
