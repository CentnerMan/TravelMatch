package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.ReasonClaim;

public interface ReasonClaimRepository extends JpaRepository<ReasonClaim,Long> {
}
