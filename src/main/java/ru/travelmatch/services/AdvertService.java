package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.travelmatch.base.entities.Advert;
import ru.travelmatch.base.repo.AdvertRepository;
import ru.travelmatch.dto.AdvertDto;

import java.util.List;

@Service
@AllArgsConstructor
public class AdvertService {
    private AdvertRepository advertRepository;

    public List<AdvertDto> getAllDtos() {
        return advertRepository.findAllBy();
    }

    public Advert findById(Long id){
        return advertRepository.findAdvertById(id);
    }
    @Transactional
    public Advert save(Advert advert){
        return  advertRepository.save(advert);
    }
    @Transactional
    public void deleteById(Long id){advertRepository.deleteById(id);}
}
