package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.Language;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */

@Getter
public class LanguageDTO {
    private Long id;
    private String name;

    public LanguageDTO(Language language) {
        this.id = language.getId();
        this.name = language.getName();
    }
}
