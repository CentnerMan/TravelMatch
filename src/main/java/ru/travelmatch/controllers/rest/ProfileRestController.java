/**
 * @author Ostrovskiy Dmitriy
 * @created 11.06.2020
 * ProfileRrestController
 * @version v1.0
 */

package ru.travelmatch.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.dto.FileDto;
import ru.travelmatch.dto.ProfileFreeGetDto;
import ru.travelmatch.dto.ProfilePersonalDto;
import ru.travelmatch.exception.ValidationErrorBuilder;
import ru.travelmatch.jwt.JwtTokenProvider;
import ru.travelmatch.services.UserServiceImpl;
import ru.travelmatch.utils.SystemUser;

import javax.validation.Valid;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/profile")
@CrossOrigin(origins = "*")
public class ProfileRestController {

    private AuthenticationManager authenticationManager;
    private UserServiceImpl userService;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public ProfileRestController(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    /**
     * Метод возвращает профиль пользователя по id пользователя
     *
     * @return профиль если пользователь аутентифицирован и запрашивает его для просмотра
     * @return часть профиля разрешённую для просмотра всем пользователям если нет авторизации
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getIdUser(@PathVariable(name = "id") Long id, Principal principal,
                                       @RequestHeader(value = "Authorization", required = false) String header) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>("Not Found!", HttpStatus.NOT_FOUND);
        }
        ProfilePersonalDto getUser = new ProfilePersonalDto(user);
        // Если пользователь не авторизован, то return ProfileFreeGetDto
        if (user!= null && (principal==null || !principal.getName().equals(user.getUsername()))) {
            ProfileFreeGetDto freeGetDto = new ProfileFreeGetDto(user);
            return new ResponseEntity<ProfileFreeGetDto>(freeGetDto, HttpStatus.OK);
        }
        return new ResponseEntity<ProfilePersonalDto>(getUser, HttpStatus.OK);
    }

    /**
     * Метод создаёт профиль пользователя
     */
    @PostMapping("register")
    public ResponseEntity<?> saveProfile(@Valid @RequestBody SystemUser systemUser, Errors errors, Principal principal,
                                         @RequestHeader(value = "Authorization", required = false) String header) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        List<String> response = userService.isExists(systemUser);
        if (response != null) {
            return isExistsResponse(response);
        }
        User user = userService.save(systemUser);
        if (user == null) {
            return new ResponseEntity<>("Bad Request!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Profile created!", HttpStatus.CREATED);
    }

    /**
     * Метод изменяет профиль авторизованного пользователя
     *
     * @return измененный профиль пользователся
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody SystemUser systemUser, Errors errors,
                                           @PathVariable(name = "id") Long id, Principal principal,
                                           @RequestHeader(value = "Authorization", required = false) String header) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        if (principal == null || principal.getName().isEmpty() || id.equals(userService.findByUsername(principal.getName()).getId())) {
            return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        }
        if ( id == systemUser.getId() && userService.isUsernameExist(systemUser.getUsername())) {
            List<String> response = userService.repeatExist(systemUser);
            if (response != null) {
                return isExistsResponse(response);
            }
            User user = userService.update(systemUser);
            if (user != null) {
                ProfilePersonalDto getUser = new ProfilePersonalDto(userService.findByUsername(systemUser.getUsername()));
                return new ResponseEntity<ProfilePersonalDto>(getUser, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("{id}/upload")
    public ResponseEntity<?> saveFile(@Valid @RequestBody FileDto fileDto, Errors errors, Principal principal,
                                      @PathVariable(name = "id") Long id,
                                      @RequestHeader(value = "Authorization", required = false) String header) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        if (principal == null || principal.getName().isEmpty() || id.equals(userService.findByUsername(principal.getName()).getId())) {
            return new ResponseEntity<>("Bad authorized!", HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findById(id);
        // TODO Для хранения информации о загружаемых файлах пользователя нужено добавить колонки в таблицу БД users_pictURLs до вида(id, name, created, url) или более.
        // TODO нужна сущность upload или file?
        // TODO нужен сервис для работы с файлами.
        return new ResponseEntity<>("Image Upload!", HttpStatus.OK);
    }

    private ResponseEntity<?> isExistsResponse(List<String> response) {
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>("Bad Requests!", HttpStatus.BAD_REQUEST);
    }
}
