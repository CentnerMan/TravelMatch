package ru.travelmatch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.travelmatch.base.entities.Article;
import ru.travelmatch.base.repo.ArticleRepository;

/**
 * @Author Farida Gareeva
 * Created 19.07.2020
 * v1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    private ArticleRepository articleRepository;

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Article> findAll(Specification<Article> specification, PageRequest pageRequest) {
        return articleRepository.findAll(specification, pageRequest);
    }
}
