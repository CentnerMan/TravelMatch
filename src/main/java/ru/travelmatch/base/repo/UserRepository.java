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

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phone);

    Optional<User> findOneById(Long id);
    Optional<User> findOneByUsername(String username);
    Optional<User> findUserByUsername(String username);
    Optional<User> findOneByPhoneNumber(String phone);
    Optional<User> findOneByEmail(String email);  

    User findOneByEmailOrPhoneNumber(String email, String phone);
  
    List<User> findAll(Specification<User> specification);

}
