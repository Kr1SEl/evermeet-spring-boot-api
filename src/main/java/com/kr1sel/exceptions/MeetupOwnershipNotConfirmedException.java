package com.kr1sel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "User is not an owner of a meetup")
public class MeetupOwnershipNotConfirmedException extends Exception{
}
