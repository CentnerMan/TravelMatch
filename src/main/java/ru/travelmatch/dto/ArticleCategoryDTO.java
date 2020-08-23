package ru.travelmatch.dto;

import lombok.Getter;
import ru.travelmatch.base.entities.ArticleCategory;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 */
@Getter
public class ArticleCategoryDTO {
    private Long id;
    private String name;

    public ArticleCategoryDTO(ArticleCategory category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
