package ru.travelmatch.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.Article;

/**
 * @Author Farida Gareeva
 * Created 19.07.2020
 * v1.0
 */

public interface ArticleService {
    Page<Article> findAll(Specification<Article> specification, PageRequest pageRequest);
}
