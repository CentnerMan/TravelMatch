package ru.travelmatch.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.travelmatch.base.entities.City;
import ru.travelmatch.services.CityService;

import java.util.List;

@RestController
@RequestMapping("api/v1/cities")
@Api("Set of endpoints for cities")
@AllArgsConstructor
public class CitiesRestController {
    private CityService cityService;
    @GetMapping
    @ApiOperation("Return list of cities")
    public List<City> getCitiesList() {
        return cityService.getAll();
    }
}
