package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.ArticleCategory;

import java.io.Serializable;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */
@Getter
public class ArticleCategoryDTO implements Serializable {

    private static final long serialVersionUID = -90027545L;

    private Long id;
    private String name;

    public ArticleCategoryDTO(ArticleCategory category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
