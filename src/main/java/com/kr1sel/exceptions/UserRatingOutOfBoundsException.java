package com.kr1sel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Rating should be in range 1 to 5")
public class UserRatingOutOfBoundsException extends Exception{
}
