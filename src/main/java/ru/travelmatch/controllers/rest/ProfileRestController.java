/**
 * @author Ostrovskiy Dmitriy
 * @created 11.06.2020
 * ProfileRrestController
 * @version v1.0
 */

package ru.travelmatch.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.base.entities.Role;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.dto.AuthDto;
import ru.travelmatch.dto.ProfileGetDto;
import ru.travelmatch.jwt.JwtTokenProvider;
import ru.travelmatch.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/profile")
@CrossOrigin(origins = "*")
public class ProfileRestController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public ProfileRestController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    public String profile(@Valid @RequestBody ProfileGetDto profile) {
        try {
            String username = profile.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, profile.getPassword()));
            User user = userService.findByUsername(username);
            return tokenProvider.createToken(username, (List<Role>) user.getRoles());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неправильное имя пользователя или пароль");
        }
    }
}