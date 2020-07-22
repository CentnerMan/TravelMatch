package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Advert;

public interface AdvertRepository extends JpaRepository<Advert,Long> {
}
