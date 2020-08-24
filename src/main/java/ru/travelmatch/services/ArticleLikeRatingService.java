package ru.travelmatch.services;

import ru.travelmatch.base.entities.ArticleLikeRating;
import ru.travelmatch.dto.ArticleLikeRatingDTO;

import java.util.List;
import java.util.Optional;

/**
 * @Author Farida Gareeva
 * Created 23.08.2020
 * v1.0
 */
public interface ArticleLikeRatingService {
    ArticleLikeRating save(ArticleLikeRatingDTO articleLikeRating);
    List<ArticleLikeRatingDTO> getAll();
    void delete(Long article_id,Long user_id);
}
