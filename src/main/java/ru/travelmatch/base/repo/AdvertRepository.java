package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Advert;
import ru.travelmatch.dto.AdvertDto;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    List<AdvertDto> findAllBy();
}
