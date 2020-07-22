package ru.travelmatch.controllers.rest;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestController
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<SimpleException> handleInvalidAuth(AuthenticationException e) {
        return new ResponseEntity<>(new SimpleException("Неправильное имя пользователя или пароль"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<SimpleException> handleInvalidAuth2(BadCredentialsException e) {
        return new ResponseEntity<>(new SimpleException("Неправильное имя пользователя или пароль"),
                HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class SimpleException {
       private String message;
    }
}
