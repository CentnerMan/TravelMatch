package ru.travelmatch.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Stanislav Ryzhkov
 * created on 21.03.2020
 */

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
