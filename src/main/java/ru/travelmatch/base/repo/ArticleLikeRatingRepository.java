package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.ArticleLikeRating;

public interface ArticleLikeRatingRepository extends JpaRepository<ArticleLikeRating, ArticleLikeRating.ArticleLikeRatingId> {
}
