package ru.travelmatch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.travelmatch.base.entities.Article;

import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 19.07.2020
 * ДТО сущности Article для целей фильтрации
 * v1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ArticleSimpleDTO {

    private Long id;

    private String title;

    private String text;

    private Long categoryId;

    private String categoryName;

    private Long authorId;

    private String authorUsername;

    private LocalDateTime created;

    private LocalDateTime lastUpdated;

    private Long cityId;

    private String cityCountryName;

    private Long languageId;

    private String languageName;

    public ArticleSimpleDTO(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.text = article.getText();
        this.categoryId = article.getCategory().getId();
        this.categoryName = article.getCategory().getName();
        this.authorId = article.getAuthor().getId();
        this.authorUsername = article.getAuthor().getUsername();
        this.created = article.getCreated();
        this.lastUpdated = article.getLastUpdated();
        this.cityId = article.getCity().getId();
        this.cityCountryName = article.getCity().getCountry().getName()+", "+article.getCity().getName();
        this.languageId = article.getLanguage().getId();
        this.languageName = article.getLanguage().getName();
    }
}
