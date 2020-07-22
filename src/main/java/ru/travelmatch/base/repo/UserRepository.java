package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.travelmatch.base.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByUsername(String username);
    Optional<User> findUserByUsername(String username);
    Optional<User> findOneByPhoneNumber(String phone);
    Optional<User> findOneByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findOneById(Long id);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phone);

    User findOneByEmailOrPhoneNumber(String email, String phone);
}
