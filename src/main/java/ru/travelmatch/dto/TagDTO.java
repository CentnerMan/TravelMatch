package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.Tag;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */

@Getter
public class TagDTO {
    private Long id;
    private String name;

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
