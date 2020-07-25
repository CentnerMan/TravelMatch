package ru.travelmatch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.travelmatch.base.entities.Article;
import ru.travelmatch.base.repo.ArticleRepository;

import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 19.07.2020
 * v1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> findAll(Specification<Article> specification) {
        return articleRepository.findAll(specification);
    }
}
