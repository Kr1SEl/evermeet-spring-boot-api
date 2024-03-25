package com.kr1sel.models;


import com.kr1sel.utils.AppUserRole;
import com.kr1sel.utils.Interest;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class AppUser extends AbstractModel implements UserDetails {

    private String name;
    private String username;
    private String password;
    private int age;
    private String location;
    private boolean isActive;
    private Set<Interest> interests;
    @Enumerated(value = EnumType.STRING)
    private AppUserRole userRole;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Meetup> meetups;

    @ManyToMany
    @JoinTable(
            name = "user_connections",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<AppUser> friends;

    public AppUser(String name, String username, String password, int age, String location) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.isActive = true;
        this.location = location;
        userRole = AppUserRole.ROLE_USER;
    }



    @Override
    public String toString() {
        return "Person{" +
                "name='" + username + '\'' +
                ", age=" + age +
                ", location='" + location + '\'' +
                ", interests=" + interests +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
