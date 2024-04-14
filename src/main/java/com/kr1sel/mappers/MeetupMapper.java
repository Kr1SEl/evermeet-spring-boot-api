package com.kr1sel.mappers;

import com.kr1sel.dto.MeetupDTO;
import com.kr1sel.models.Meetup;

import java.util.function.Function;

public class MeetupMapper implements Function<Meetup, MeetupDTO> {
    @Override
    public MeetupDTO apply(Meetup meetup) {
        return new MeetupDTO(
                meetup.getEventName(),
                meetup.getLocation(),
                meetup.isPrivate(),
                meetup.getMaxPeople(),
                meetup.getAuthor().getUsername()
        );
    }
}
