package com.kr1sel.controllers;

import com.kr1sel.dto.MeetupDTO;
import com.kr1sel.dto.NewMeetupRequestDTO;
import com.kr1sel.exceptions.*;
import com.kr1sel.models.AppUser;
import com.kr1sel.services.MeetupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/meetup")
public class MeetupController {

    private final MeetupService meetupService;

    @Autowired
    public MeetupController(MeetupService meetupService){
        this.meetupService = meetupService;
    }

    @GetMapping
    public List<MeetupDTO> getFilteredMeetups(@AuthenticationPrincipal AppUser user){
        return meetupService.getMeetupsFilteredByInterests(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createMeetup(@RequestBody @Valid NewMeetupRequestDTO newMeetup,
                               @AuthenticationPrincipal AppUser user,
                               HttpServletRequest request){
       return request.getRequestURI() + meetupService.createNewMeetup(newMeetup, user);
    }

    @GetMapping(path = "{id}")
    public MeetupDTO getMeetupById(@PathVariable Long id) throws MeetupNotFoundException {
        return meetupService.getMeetupById(id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteMeetupById(@PathVariable Long id, @AuthenticationPrincipal AppUser user)
            throws MeetupNotFoundException, MeetupOwnershipNotConfirmedException {
        meetupService.deleteMeetupById(id, user);
    }

    @GetMapping(path = "{id}/participants")
    public Set<AppUser> getAttendeesByMeetupId(@PathVariable Long id)
            throws MeetupNotFoundException{
        return meetupService.getMeetupAttendeesByMeetupId(id);
    }

    @PutMapping(path = "{id}/participate")
    public void subscribeUserToMeetup(@PathVariable Long id,
                                      @AuthenticationPrincipal AppUser appUser)
            throws MeetupNotFoundException, MeetupIsFullException, UserAlreadySubscribedToMeetupException {
        meetupService.addMeetupParticipant(id, appUser);
    }

    @PutMapping(path = "{id}/abandon")
    public void unsubscribeUserFromMeetup(@PathVariable Long id,
                                          @AuthenticationPrincipal AppUser appUser)
            throws MeetupNotFoundException, UserNotSubscribedToMeetupException {
        meetupService.removeMeetupParticipant(id, appUser);
    }
}
