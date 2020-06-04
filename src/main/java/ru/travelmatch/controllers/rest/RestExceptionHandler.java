package ru.travelmatch.controllers.rest;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Stanislav Ryzhkov
 * created on 21.03.2020
 */

@RestController
@ControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(AuthenticationException.class)
    public String handleInvalidAuth(AuthenticationException e) {
        return "Неправильное имя пользоветеля или пароль";
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public String handleInvalidAuth2(BadCredentialsException e) {
        return "Неправильное имя пользоветеля или пароль";
    }
}
