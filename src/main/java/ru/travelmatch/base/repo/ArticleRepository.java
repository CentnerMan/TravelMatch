package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Article;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
