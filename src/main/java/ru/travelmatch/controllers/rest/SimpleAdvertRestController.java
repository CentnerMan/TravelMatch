package ru.travelmatch.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public SimpleAdvert getAdvertById(@PathVariable Long id) throws Exception {
        SimpleAdvert advert=simpleAdvertService.findById(id);
        return advert;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SimpleAdvert addAdvert(@RequestBody SimpleAdvert advert) throws Exception {
        simpleAdvertService.save(advert);
        return advert;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        simpleAdvertService.deleteById(id);
    }
}
