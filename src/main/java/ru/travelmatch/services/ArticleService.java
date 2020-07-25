package ru.travelmatch.services;

import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.Article;

import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 19.07.2020
 * v1.0
 */

public interface ArticleService {
    List<Article> findAll(Specification<Article> specification);
}
