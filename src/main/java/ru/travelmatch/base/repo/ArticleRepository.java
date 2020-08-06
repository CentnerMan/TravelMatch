package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.travelmatch.base.entities.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>,JpaSpecificationExecutor<Article> {

}
