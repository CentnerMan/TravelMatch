package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.City;

import java.io.Serializable;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */
@Getter
public class CityDTO implements Serializable {

    private static final long serialVersionUID = -90000070L;

    private Long cityId;
    private String cityName;
    private String countryName;
    private Long countryId;

    public CityDTO(City city) {
        this.cityId = city.getId();
        this.cityName = city.getName();
        this.countryName = city.getCountry().getName();
        this.countryId = city.getCountry().getId();
    }
}
