package ru.travelmatch.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.services.UserService;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */


/**
 * Класс в состоянии тестирования
 */

@RestController
@RequestMapping(value = "/api/v1/users")
public class UsersRestController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<String> getUsers() throws Exception {
        try {
            List<User> userList = userService.findAll();
            ArrayList<String> list = new ArrayList<>();
            for (User user : userList) {
                list.add(user.getUsername());
            }
            return list;

        } catch (Exception e) {
            throw new Exception("Что-то пошло не так");
        }
    }

}
