package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.ArticleCategory;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory,Long> {
}
