package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Award;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */
public interface AwardRepository extends JpaRepository<Award,Long> {
}
