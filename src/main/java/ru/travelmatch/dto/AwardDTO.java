package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.Award;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */

@Getter
public class AwardDTO {
    private Long id;
    private String title;

    public AwardDTO(Award award) {
        this.id = award.getId();
        this.title = award.getTitle();
    }
}
