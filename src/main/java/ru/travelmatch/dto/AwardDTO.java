package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.Award;

import java.io.Serializable;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */

@Getter
public class AwardDTO implements Serializable {

    private static final long serialVersionUID = -90000059L;

    private Long id;
    private String title;

    public AwardDTO(Award award) {
        this.id = award.getId();
        this.title = award.getTitle();
    }
}
