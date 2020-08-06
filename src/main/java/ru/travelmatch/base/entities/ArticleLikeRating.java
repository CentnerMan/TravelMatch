package ru.travelmatch.base.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 08/06/2020
 * v1.0
 * сущность для хранения лайков статей авторизованных пользователей
 * ключ уникальности - (article,user)
 * likeDislike принимает значения +1 (в случае лайка), -1 (в случае дизлайка).
 */

@NoArgsConstructor
@Entity
@Table(name = "article_likes_ratings")
@Data
@IdClass(ArticleLikeRating.ArticleLikeRatingId.class)
public class ArticleLikeRating {

    @Id
    @Column(name = "article_id", nullable = false)
    private Long article;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long user;

    @Column(name = "like_dislike")
    private int likeDislike;

    @Column(name = "rating")
    private int rating;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @EqualsAndHashCode
    @Access(value = AccessType.FIELD)
    @Setter
    public static class ArticleLikeRatingId implements Serializable {
        private Long article;
        private Long user;
    }
}
