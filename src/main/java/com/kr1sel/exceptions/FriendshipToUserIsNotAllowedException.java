package com.kr1sel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "You cannot send friendship request to this user")
public class FriendshipToUserIsNotAllowedException extends Exception{
}
