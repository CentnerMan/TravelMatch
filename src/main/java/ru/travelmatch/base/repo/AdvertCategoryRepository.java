package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.AdvertCategory;

public interface AdvertCategoryRepository extends JpaRepository<AdvertCategory,Long> {
    AdvertCategory findOneById(Long id);
}
