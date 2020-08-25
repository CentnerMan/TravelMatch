package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.travelmatch.base.entities.City;
import ru.travelmatch.base.repo.CityRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {
    private CityRepository cityRepository;

    public List<City> getAll() {
        return cityRepository.findAll();
    }

}
