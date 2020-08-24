package ru.travelmatch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.travelmatch.base.entities.ArticleLikeRating;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 23.08.2020
 * v1.0
 */
@Getter
@NoArgsConstructor
public class ArticleLikeRatingDTO implements Serializable {

    private Long article;
    private Long user;
    private Integer likeDislike;
    private Integer rating;
    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    public ArticleLikeRatingDTO(ArticleLikeRating values) {
        this.article = values.getArticle();
        this.user = values.getUser();
        this.likeDislike = values.getLikeDislike();
        this.rating = values.getRating();
        this.created = values.getCreated();
        this.lastUpdated = values.getLastUpdated();
    }
}
