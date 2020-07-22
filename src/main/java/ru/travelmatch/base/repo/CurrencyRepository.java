package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Currency;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {
}
