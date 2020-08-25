package ru.travelmatch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.travelmatch.base.entities.ArticleLikeRating;

import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
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

    private static final long serialVersionUID = 4384862063723384523L;

    @NotNull(message = "Article Id shouldn't be null!")
    private Long article;

    @NotNull(message = "User Id shouldn't be null!")
    private Long user;

    private Integer likeDislike;
    private Integer rating;

    private LocalDateTime created;
    private LocalDateTime lastUpdated;

    @Transient
    private boolean likeValid;

    @Transient
    private boolean ratingValid;

    @AssertTrue(message = "The value of like or dislike should be 1 or -1")
    private boolean isLikeValid(){
        return likeDislike==1||likeDislike==-1;
    }

    @AssertTrue(message = "The value of rating should be Integer from 1 to 5")
    private boolean isRatingValid(){
        return rating>=1&&rating<=5;
    }

    public ArticleLikeRatingDTO(ArticleLikeRating values) {
        this.article = values.getArticle();
        this.user = values.getUser();
        this.likeDislike = values.getLikeDislike();
        this.rating = values.getRating();
        this.created = values.getCreated();
        this.lastUpdated = values.getLastUpdated();
    }
}
