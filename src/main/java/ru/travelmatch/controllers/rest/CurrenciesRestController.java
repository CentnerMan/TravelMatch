package ru.travelmatch.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.travelmatch.base.entities.Currency;
import ru.travelmatch.services.CurrencyService;

import java.util.List;

@RestController
@RequestMapping("api/v1/currencies")
@Api("Set of endpoints for currencies")
@AllArgsConstructor
public class CurrenciesRestController {
    private CurrencyService currencyService;
    @GetMapping
    @ApiOperation("Return list of currencies")
    public List<Currency> getCitiesList() {
        return currencyService.getAll();
    }
}
