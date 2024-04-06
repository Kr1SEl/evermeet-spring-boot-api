package com.kr1sel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User is not allowed to grade himself")
public class UserIsOwnerOfTheRatingException extends Exception{
}
