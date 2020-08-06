package ru.travelmatch.services;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetailsService;

import ru.travelmatch.base.entities.User;
import ru.travelmatch.utils.SystemUser;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean isUserExist(String username);
    boolean isUsernameExist(String username);
    boolean isEmailExist(String email);
    boolean isPhoneExist(String phone);
  
    User isUserEmailAndPhoneExists(SystemUser systemUser);
  
    User findById(Long id);
    User findByUsername(String username);
    User findByPhone(String phone);
    User findByEmail(String email);   
  
    User save(SystemUser systemUser);
    User update(SystemUser systemUser);
    void delete(Long id);

    List<User> findAll();
    List<User> findAll(Specification<User> specification);
  
    List<String> isExists(SystemUser systemUser);
    List<String> repeatExist(SystemUser systemUser);
    
}
