package ru.travelmatch.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для /admin
 * @author  Vladimir Levin
 * created on 14.03.2020
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/newsadding")
    public String addnews() {
        return "admin/addnews";
    }

}
