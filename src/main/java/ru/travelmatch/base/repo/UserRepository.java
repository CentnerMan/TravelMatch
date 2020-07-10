package ru.travelmatch.base.repo;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.travelmatch.base.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findOneByUsername(String username);

    boolean existsByUsername(String username);

    User findOneById(Long id);

  boolean existsByEmail(String email);
  Optional<User> findUserByUsername(String username);

    List<User> findAll(Specification<User> specification);

}
