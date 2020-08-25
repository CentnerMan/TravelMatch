package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.travelmatch.base.entities.Country;
import ru.travelmatch.base.repo.CountryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryService {
    private CountryRepository countryRepository;
    public List<Country> getAll() {return countryRepository.findAll();
    }
}
