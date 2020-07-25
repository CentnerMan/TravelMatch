package ru.travelmatch.services;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @version 1.0.0 11.06.2020
 * @link https://github.com/Centnerman
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.travelmatch.base.entities.Role;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.base.repo.UserRepository;
import ru.travelmatch.base.repo.RoleRepository;
import ru.travelmatch.utils.SystemUser;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    private final String USER_EXIST = "User with this name already exists!";
    private final String PHONE_EXIST = "Phone already exists!";
    private final String EMAIL_EXIST = "Email already exists!";

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public User  findByPhone(String phone) {
        return userRepository.findOneByPhoneNumber(phone).orElse(null);
    }

    @Override
    @Transactional
    public User  findByEmail(String email) {
        return userRepository.findOneByEmail(email).orElse(null);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        return userRepository.findOneById(id).orElse(null);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(userName).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean isUserExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isPhoneExist(String phone) {
        return userRepository.existsByPhoneNumber(phone);
    }

    @Override
    @Transactional
    public List<String> isExists(SystemUser systemUser) {
        List<String> response = new ArrayList<>();
        if (findByUsername(systemUser.getUsername())!=null) response.add(USER_EXIST);
        if (isPhoneExist(systemUser.getPhoneNumber())) response.add(PHONE_EXIST);
        if (isEmailExist(systemUser.getEmail())) response.add(EMAIL_EXIST);
        return  response.isEmpty()? null : response;
    }

    /**
     * Метод проверяет повтор данных в БД у других пользователей
     * вводимые данные могут повторяться с данными только самого пользователя(для update)
     * @return String если есть повтор
     * @return null если нет повторов в БД
     */
    @Override
    @Transactional
    public List<String> repeatExist(SystemUser systemUser) {
        String username = systemUser.getUsername();
        User user = findByUsername(username);
        List<String> response = new ArrayList<>();
        if (user!=null) {
            User userWithName = findByUsername(systemUser.getUsername());
            User userWithPhone = findByPhone(systemUser.getPhoneNumber());
            User userWithEmail = findByEmail(systemUser.getEmail());
            if (userWithName != null && !userWithName.equals(user)) response.add(USER_EXIST);
            if (userWithPhone != null && !userWithPhone.equals(user)) response.add(PHONE_EXIST);
            if (userWithEmail != null && !userWithEmail.equals(user)) response.add(EMAIL_EXIST);
        }
        return  response.isEmpty()? null : response;
    }

    @Override
    public User isUserEmailAndPhoneExists(SystemUser systemUser) {
        return userRepository.findOneByEmailOrPhoneNumber(systemUser.getEmail(), systemUser.getPhoneNumber());
    }

    @Override
    @Transactional
    public User save(SystemUser systemUser) {
        if (findByUsername(systemUser.getUsername()) != null) {
            throw new RuntimeException("User with username " + systemUser.getUsername() + " is already exist");
        }
        User user = new User(systemUser);
        if (systemUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        }

        Collection<Role> rolesAll = roleRepository.findAll();
        Role role = roleRepository.findOneByName("ROLE_USER");
        Collection<Role> roles = Arrays.asList(role);
        user.setRoles(roles);
        User userGet = userRepository.save(user);
        return userGet;
    }

    @Override
    public List<User> findAll(Specification<User> specification) {
        return userRepository.findAll(specification);
    }

    @Override
    public User update(SystemUser systemUser) {
        User user = findById(systemUser.getId());
        try {
            if (systemUser.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
            }
            user.setAll(systemUser,null);
            user = userRepository.save(user);
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserServiceImpl that = (UserServiceImpl) o;
        return Objects.equals(userRepository, that.userRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRepository);
    }
}
