package ru.travelmatch.controllers.rest;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
