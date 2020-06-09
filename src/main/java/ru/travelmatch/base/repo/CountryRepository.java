package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Country;

public interface CountryRepository extends JpaRepository<Country,Long> {
}
