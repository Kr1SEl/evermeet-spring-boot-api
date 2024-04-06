package com.kr1sel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_MODIFIED, reason = "User is already subscribed to this meetup")
public class UserAlreadySubscribedToMeetupException extends Exception{
}
