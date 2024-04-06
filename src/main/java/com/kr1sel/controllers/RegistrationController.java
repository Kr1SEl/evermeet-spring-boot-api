package com.kr1sel.controllers;

import com.kr1sel.dto.NewAppUserRequestDTO;
import com.kr1sel.exceptions.UserAlreadyExistsException;
import com.kr1sel.services.AppUserService;
import com.kr1sel.services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/auth")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    @PostMapping(path = "register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody NewAppUserRequestDTO request)
            throws UserAlreadyExistsException {
        registrationService.register(request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
