package com.kr1sel.models;


import com.kr1sel.utils.AppUserRole;
import com.kr1sel.utils.Interest;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 16)
    private String username;

    @Column(nullable = false)
    private String password;

    private short age;

    private String location;

    private boolean isActive;

    @Column(nullable = false)
    private Set<Interest> interests;

    @Enumerated(value = EnumType.STRING)
    private AppUserRole userRole;

    @OneToOne(cascade = CascadeType.REMOVE)
    private ImageMetadata userImage;

    @ManyToMany
    @JoinTable(
            name = "user_connections",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<AppUser> friends;

    @ManyToMany
    @JoinTable(
            name = "friend_requests",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<AppUser> friendRequests;

    public AppUser(String name,
                   String username,
                   String password,
                   short age,
                   String location,
                   Set<Interest> interests) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.isActive = true;
        this.location = location;
        this.interests = interests;
        this.userRole = AppUserRole.ROLE_USER;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(username, appUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public boolean isAdminUser(){
        return userRole.equals(AppUserRole.ROLE_ADMIN);
    }

    public boolean hasFriendRequestsFrom(AppUser user){
        return this.friendRequests.contains(user);
    }

    public boolean hasFriend(AppUser user){
        return this.friends.contains(user);
    }

    public void addFriend(AppUser user){
        this.friends.add(user);
    }

    public void addFriendRequestFrom(AppUser user){
        this.friendRequests.add(user);
    }

    public void removeFriend(AppUser user){
        this.friends.remove(user);
    }

    public void removeFriendRequestFrom(AppUser user){
        this.friendRequests.remove(user);
    }

    public void becomeFriendsWith(AppUser user){
        this.removeFriendRequestFrom(user);
        this.addFriend(user);
    }

}
