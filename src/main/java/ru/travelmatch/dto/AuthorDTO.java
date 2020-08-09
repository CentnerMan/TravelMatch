package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.User;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 * Класс DTO для передачи информации об авторах на сторону фронта для заполнения настроек фильтра по статьям
 * (чтобы пользователь системы мог выбрать из списка интересующего его автора)
 */

@Getter
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public AuthorDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
