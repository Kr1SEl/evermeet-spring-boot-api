package com.kr1sel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User does not have rating")
public class UserRatingNotFoundException extends Exception{
}
