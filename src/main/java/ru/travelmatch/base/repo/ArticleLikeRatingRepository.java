package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.ArticleLikeRating;

import java.util.Optional;

public interface ArticleLikeRatingRepository extends JpaRepository<ArticleLikeRating, ArticleLikeRating.ArticleLikeRatingId> {
//    @Override
//    Optional<ArticleLikeRating> findById(ArticleLikeRating.ArticleLikeRatingId articleLikeRatingId);
}
