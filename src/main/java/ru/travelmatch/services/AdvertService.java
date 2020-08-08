package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
}
