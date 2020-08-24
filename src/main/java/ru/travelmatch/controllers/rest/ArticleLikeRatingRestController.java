package ru.travelmatch.controllers.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.base.entities.ArticleLikeRating;
import ru.travelmatch.dto.ArticleLikeRatingDTO;
import ru.travelmatch.services.ArticleLikeRatingService;

import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 23.08.2020
 * v1.0
 */
@RestController
@RequestMapping("api/v1/article_like_ratings")
@AllArgsConstructor
public class ArticleLikeRatingRestController {

    private ArticleLikeRatingService service;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ArticleLikeRatingDTO> saveLike(@RequestBody ArticleLikeRatingDTO articleLikeRatingDTO) {

        ArticleLikeRating articleLikeRating = service.save(articleLikeRatingDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ArticleLikeRatingDTO(articleLikeRating));
    }

    @GetMapping
    public ResponseEntity<List<ArticleLikeRatingDTO>> getLikes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAll());
    }

    @DeleteMapping(value = "/article_id={article_id}/user_id={user_id}")
    public ResponseEntity deleteLike(@PathVariable(name = "article_id") Long articleId, @PathVariable(name = "user_id") Long userId) {
        service.delete(articleId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Like/Rating successfully removed");
    }
}
