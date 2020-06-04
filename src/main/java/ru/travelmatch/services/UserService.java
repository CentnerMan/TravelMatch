package ru.travelmatch.services;


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
