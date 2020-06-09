package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.ArticleRating;

public interface ArticleRatingRepository extends JpaRepository<ArticleRating,ArticleRating.ArticleRatingId> {
}
