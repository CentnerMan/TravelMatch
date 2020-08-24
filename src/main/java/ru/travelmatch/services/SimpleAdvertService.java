package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public SimpleAdvert findById(Long id){
        return simpleAdvertRepository.findAdvertById(id);
    }
    @Transactional
    public SimpleAdvert save(SimpleAdvert advert){
        return  simpleAdvertRepository.save(advert);
    }
    @Transactional
    public void deleteById(Long id){simpleAdvertRepository.deleteById(id);}
}
