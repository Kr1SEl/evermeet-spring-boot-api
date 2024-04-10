package com.kr1sel.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Only Admin users are allowed to fetch this data")
public class NotSufficientPrivilegesException extends Exception{
}
