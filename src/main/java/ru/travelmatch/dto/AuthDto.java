package ru.travelmatch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class AuthDto {

    @NotNull(message = "Field must not be empty!")
    @Size(min = 4, max = 100, message = "Too short <4!")
    private final String username;

    @NotNull(message = "Field must not be empty!")
    @Size(min = 4, max = 100, message = "Too short <4!")
    private final String password;
}
