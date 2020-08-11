package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.travelmatch.base.entities.SimpleAdvert;
import ru.travelmatch.base.repo.SimpleAdvertRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleAdvertService {
    private SimpleAdvertRepository simpleAdvertRepository;

    public List<SimpleAdvert> findAll() {
        return simpleAdvertRepository.findAll();
    }
}
