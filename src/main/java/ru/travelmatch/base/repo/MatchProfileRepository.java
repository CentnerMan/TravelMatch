package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.MatchProfile;

public interface MatchProfileRepository extends JpaRepository<MatchProfile,Long> {
}
