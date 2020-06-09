package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.MatchRequest;

public interface MatchRequestRepository extends JpaRepository<MatchRequest,Long> {
}
