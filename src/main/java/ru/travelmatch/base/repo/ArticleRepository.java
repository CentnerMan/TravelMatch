package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.travelmatch.base.entities.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long>,JpaSpecificationExecutor<Article> {

}
