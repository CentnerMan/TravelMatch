package ru.travelmatch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Stanislav Ryzhkov
 * created on 21.03.2020
 */

@Getter
@RequiredArgsConstructor
public class AuthDto {

    @NotNull(message = "Заполните все поля")
    @Size(min = 4, max = 100, message = "Имя пользователя от 4 до 100 символов")
    private final String username;

    @NotNull(message = "Заполните все поля")
    @Size(min = 4, max = 100, message = "Пароль от 4 до 100 символов")
    private final String password;
}
