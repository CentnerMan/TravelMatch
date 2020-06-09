package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.AdvertClaim;

public interface AdvertClaimRepository extends JpaRepository<AdvertClaim,Long> {
}
