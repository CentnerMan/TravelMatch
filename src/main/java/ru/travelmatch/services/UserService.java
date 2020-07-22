package ru.travelmatch.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import ru.travelmatch.base.entities.User;
import ru.travelmatch.utils.SystemUser;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(Long id);
    User findByUsername(String username);
    User findByPhone(String phone);
    User findByEmail(String email);
    List<User> findAll();

    boolean isUserExist(String username);
    boolean isUsernameExist(String username);
    boolean isEmailExist(String email);
    boolean isPhoneExist(String phone);

    User save(SystemUser systemUser);
    User update(SystemUser systemUser);
    void delete(Long id);

    List<String> isExists(SystemUser systemUser);
    List<String> repeatExist(SystemUser systemUser);
    User isUserEmailAndPhoneExists(SystemUser systemUser);


}
