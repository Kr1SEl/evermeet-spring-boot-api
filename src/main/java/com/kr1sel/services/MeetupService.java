package com.kr1sel.services;

import com.kr1sel.dto.MeetupDTO;
import com.kr1sel.dto.NewMeetupRequestDTO;
import com.kr1sel.exceptions.*;
import com.kr1sel.mappers.MeetupMapper;
import com.kr1sel.models.AppUser;
import com.kr1sel.models.Meetup;
import com.kr1sel.repositories.MeetupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MeetupService {
    private final MeetupRepository meetupRepository;
    private final MeetupMapper meetupMapper;

    @Autowired
    public MeetupService(MeetupRepository meetupRepository){
        this.meetupRepository = meetupRepository;
        this.meetupMapper = new MeetupMapper();
    }

    public Long createNewMeetup(NewMeetupRequestDTO newMeetup, AppUser user){
        Meetup createdMeetup = meetupRepository.save(
                new Meetup(
                newMeetup.eventName(),
                newMeetup.location(),
                newMeetup.isPrivate(),
                newMeetup.maxPeople(),
                user,
                newMeetup.interests(),
                LocalDateTime.now(),
                newMeetup.startDateTime()));
        return createdMeetup.getId();
    }

    public List<MeetupDTO> getMeetupsFilteredByInterests(AppUser user){
        return meetupRepository
                .findByInterestsAndLocationAndStartDateTimeGreaterThan(
                        user.getInterests(),
                        user.getLocation(),
                        LocalDateTime.now())
                .stream().map(meetupMapper).toList();
    }

    public MeetupDTO getMeetupById(Long id) throws MeetupNotFoundException {
        Optional<Meetup> meetup = meetupRepository.findById(id);
        if(meetup.isPresent()){
            Optional<MeetupDTO> meetupDto= meetup.map(meetupMapper);
            if(meetupDto.isPresent()){
                return meetupDto.get();
            }else{
                throw new MeetupNotFoundException();
            }
        }else{
            throw new MeetupNotFoundException();
        }
    }

    public Set<AppUser> getMeetupAttendeesByMeetupId(Long id)
            throws MeetupNotFoundException{
        Optional<Meetup> meetup = meetupRepository.findById(id);
        if(meetup.isPresent()){
            return meetup.get().getAttendees();
        }else{
            throw new MeetupNotFoundException();
        }
    }

    public void addMeetupParticipant(Long id, AppUser user)
            throws MeetupNotFoundException, MeetupIsFullException, UserAlreadySubscribedToMeetupException {
        Optional<Meetup> meetup = meetupRepository.findById(id);
        if(meetup.isPresent()){
            Meetup meetupReference = meetup.get();
            boolean userAlreadySubscribed = meetupReference.getAttendees().contains(user);
            if (!userAlreadySubscribed) {
                int numberOfAttendees = meetupReference.getAttendees().size();
                if(numberOfAttendees >= meetupReference.getMaxPeople()){
                    throw new MeetupIsFullException();
                }
                boolean userAdded = meetupReference.addAttendee(user);
                if (userAdded) {
                    meetupRepository.save(meetupReference);
                }
            }
            throw new UserAlreadySubscribedToMeetupException();
        }else{
            throw new MeetupNotFoundException();
        }
    }

    public void removeMeetupParticipant(Long id, AppUser user)
            throws MeetupNotFoundException, UserNotSubscribedToMeetupException {
        Optional<Meetup> meetup = meetupRepository.findById(id);
        if(meetup.isPresent()){
            Meetup meetupReference = meetup.get();
            boolean userAlreadySubscribed = meetupReference.getAttendees().contains(user);
            if (!userAlreadySubscribed) {
                throw new UserNotSubscribedToMeetupException();
            }else{
                meetupReference.removeAttendee(user);
                meetupRepository.save(meetupReference);
            }
        }else{
            throw new MeetupNotFoundException();
        }
    }

    public void deleteMeetupById(Long id, AppUser user)
            throws MeetupNotFoundException, MeetupOwnershipNotConfirmedException {
        Optional<Meetup> meetup = meetupRepository.findById(id);
        if(meetup.isPresent()){
            Meetup meetupReference = meetup.get();
            if(meetupReference.getAuthor().equals(user)){
                meetupRepository.deleteById(id);
            }else{
                throw new MeetupOwnershipNotConfirmedException();
            }
        }else{
            throw new MeetupNotFoundException();
        }
    }

}
