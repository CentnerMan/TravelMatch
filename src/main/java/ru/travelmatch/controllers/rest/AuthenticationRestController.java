package ru.travelmatch.controllers.rest;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.base.entities.Role;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.dto.AuthDto;
import ru.travelmatch.jwt.JwtTokenProvider;
import ru.travelmatch.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthenticationRestController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTokenProvider(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public String auth(@Valid @RequestBody AuthDto auth) {
        try {
            String username = auth.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, auth.getPassword()));
            User user = userService.findByUsername(username);
            return tokenProvider.createToken(username, (List<Role>) user.getRoles());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неправильное имя пользователя или пароль");
        }
    }
}
