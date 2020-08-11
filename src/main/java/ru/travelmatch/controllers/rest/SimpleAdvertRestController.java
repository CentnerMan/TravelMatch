package ru.travelmatch.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.travelmatch.base.entities.SimpleAdvert;
import ru.travelmatch.services.SimpleAdvertService;

import java.util.List;

@RestController
@RequestMapping("api/v1/simple_adverts")
@AllArgsConstructor
public class SimpleAdvertRestController {
    private SimpleAdvertService simpleAdvertService;

    @GetMapping
    public List<SimpleAdvert> getAllAdverts() {
        return simpleAdvertService.findAll();
    }

    @GetMapping("/types")
    public SimpleAdvert.Type[] getTypesList() {
        return SimpleAdvert.Type.values();
    }
}
