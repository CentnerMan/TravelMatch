package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.travelmatch.base.entities.Currency;
import ru.travelmatch.base.repo.CurrencyRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CurrencyService {
    private CurrencyRepository currencyRepository;

    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }
}
