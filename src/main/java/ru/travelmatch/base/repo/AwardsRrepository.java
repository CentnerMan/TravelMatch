/**
 * @author Ostrovskiy Dmitriy
 * @created 22.07.2020
 * AwardsRrepository
 * @version v1.0
 */

package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.AwardsUser;

public interface AwardsRrepository extends JpaRepository<AwardsUser, AwardsUser.UserAwardsId> {
}
