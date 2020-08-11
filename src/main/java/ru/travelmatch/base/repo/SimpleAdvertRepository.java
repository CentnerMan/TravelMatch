package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.travelmatch.base.entities.SimpleAdvert;

@Repository
public interface SimpleAdvertRepository extends JpaRepository<SimpleAdvert, Long> {
}
