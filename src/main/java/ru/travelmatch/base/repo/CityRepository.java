package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.City;

public interface CityRepository extends JpaRepository<City,Long> {
}
