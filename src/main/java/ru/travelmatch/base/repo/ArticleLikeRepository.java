package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.ArticleLike;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike,ArticleLike.ArticleLikeId> {
}
