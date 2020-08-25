package ru.travelmatch.services;

import lombok.AllArgsConstructor;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.travelmatch.base.entities.ArticleLikeRating;
import ru.travelmatch.base.repo.ArticleLikeRatingRepository;
import ru.travelmatch.dto.ArticleLikeRatingDTO;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
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
    private UserService userService;
    private ArticleService articleService;
    private HttpServletResponse response;

    @Override
//    @Transactional
    public ResponseEntity save(ArticleLikeRatingDTO dto) {
        try {
            ArticleLikeRating articleLikeRating = getArticleLikeRating(dto);
            articleLikeRating.setArticle(dto.getArticle());
            articleLikeRating.setUser(dto.getUser());
            articleLikeRating.setLikeDislike(dto.getLikeDislike());
            articleLikeRating.setRating(dto.getRating());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ArticleLikeRatingDTO(repository.save(articleLikeRating)));
        } catch (NonTransientDataAccessException ex) {
            sendErrorToResponse(ex.getCause().getCause().getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("");
        } catch (EntityNotFoundException ex) {
            sendErrorToResponse(ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("");
        } catch (Exception ex) {
            sendErrorToResponse(dto + " wasn't saved \n" + ex.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("");
        }

    }

    private void sendErrorToResponse(String msg) {
        try {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private ArticleLikeRating getArticleLikeRating(ArticleLikeRatingDTO dto) throws EntityNotFoundException {
        ArticleLikeRating articleLikeRating = repository
                .findById(new ArticleLikeRating.ArticleLikeRatingId(dto.getArticle(), dto.getUser()))
                .orElse(null);

        if (articleLikeRating == null) {
            StringJoiner stringJoiner = new StringJoiner("\n");
            //поищем, если ли такие user и article
            if (userService.findById(dto.getUser()) == null) {
                stringJoiner.add("User with id = " + dto.getUser() + " not found");
            }
            if (articleService.findById(dto.getArticle()) == null) {
                stringJoiner.add("Article with id = " + dto.getArticle() + " not found");
            }
            if(stringJoiner.length()>0){
            //    sendErrorToResponse(stringJoiner.toString());
                throw new EntityNotFoundException(stringJoiner.toString());
            }
            articleLikeRating = new ArticleLikeRating();
        }
        return articleLikeRating;
    }

    @Override
    public List<ArticleLikeRatingDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(ArticleLikeRatingDTO::new)
                .collect(Collectors.toList());
    }

    @Override
//    @Transactional
    public void delete(Long article_id, Long user_id) {
        ArticleLikeRating.ArticleLikeRatingId id = new ArticleLikeRating.ArticleLikeRatingId(article_id, user_id);
        ArticleLikeRating like = repository.findById(id).orElse(null);
        if (like == null) {
            sendErrorToResponse("Like/Dislike/Rating of article_id = " + article_id + " and user_id = " + user_id
                    + " couldn't be deleted because it isn't exists");
        }
        repository.deleteById(id);
    }
}
