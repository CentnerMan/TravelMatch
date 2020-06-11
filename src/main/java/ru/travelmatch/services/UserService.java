package ru.travelmatch.services;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.travelmatch.base.entities.User;
import ru.travelmatch.utils.SystemUser;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    boolean isUserExist(String username);

    User update(SystemUser systemUser);

    List<User> findAll();

    void delete(Long id);

    User findByUsername(String username);

    boolean isUsernameExist(String username);

    boolean isEmailExist(String email);

    User save(SystemUser systemUser);


}
