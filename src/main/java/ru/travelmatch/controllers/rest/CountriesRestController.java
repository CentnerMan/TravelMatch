package ru.travelmatch.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.travelmatch.base.entities.Country;
import ru.travelmatch.services.CountryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/countries")
@Api("Set of endpoints for countries")
@AllArgsConstructor
public class CountriesRestController {
        private CountryService countryService;
        @GetMapping
        @ApiOperation("Return list of countries")
        public List<Country> getCountriesList() {
            return countryService.getAll();
        }
}
