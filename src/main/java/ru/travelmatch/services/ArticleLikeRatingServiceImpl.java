package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.travelmatch.base.entities.ArticleLikeRating;
import ru.travelmatch.base.repo.ArticleLikeRatingRepository;
import ru.travelmatch.dto.ArticleLikeRatingDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 23.08.2020
 * v1.0
 */
@Service
@AllArgsConstructor
public class ArticleLikeRatingServiceImpl implements ArticleLikeRatingService {

    private ArticleLikeRatingRepository repository;

    @Override
    public ArticleLikeRating save(ArticleLikeRatingDTO dto) {
        ArticleLikeRating articleLikeRating = repository
                .findById(new ArticleLikeRating.ArticleLikeRatingId(dto.getArticle(), dto.getUser()))
                .orElse(new ArticleLikeRating());
        articleLikeRating.setArticle(dto.getArticle());
        articleLikeRating.setUser(dto.getUser());
        articleLikeRating.setLikeDislike(dto.getLikeDislike());
        articleLikeRating.setRating(dto.getRating());
        return repository.save(articleLikeRating);
    }

    @Override
    public List<ArticleLikeRatingDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(ArticleLikeRatingDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long article_id, Long user_id) {
        repository.deleteById(new ArticleLikeRating.ArticleLikeRatingId(article_id, user_id));
    }
}
