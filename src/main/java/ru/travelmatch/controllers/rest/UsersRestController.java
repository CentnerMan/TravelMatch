package ru.travelmatch.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.services.UserService;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

@RestController
@RequestMapping(value = "/api/v1/users")
@AllArgsConstructor
public class UsersRestController {
    private UserService userService;

    @GetMapping
    public List<String> getUsers() {
        return Collections.unmodifiableList(userService.findAll().stream().map(User::getUsername).collect(Collectors.toList()));
    }
}
