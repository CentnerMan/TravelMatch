package ru.travelmatch.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.dto.ArticleLikeRatingDTO;
import ru.travelmatch.exception.ValidationErrorBuilder;
import ru.travelmatch.services.ArticleLikeRatingService;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 23.08.2020
 * v1.0
 */
@RestController
@RequestMapping(value = "api/v1/article_like_ratings")
@AllArgsConstructor
@Api("Set of endpoints for crud operation over likes, dislikes and article ratings.")
public class ArticleLikeRatingRestController {

    private ArticleLikeRatingService service;

    @ApiOperation("POST new like or dislike and value of rating for article")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity saveLike(@Valid @RequestBody ArticleLikeRatingDTO articleLikeRatingDTO, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ValidationErrorBuilder.fromBindingErrors(errors));
        }
        return service.save(articleLikeRatingDTO);
    }

    @ApiOperation("GET list of all likes, dislikes and  article ratings")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ArticleLikeRatingDTO>> getLikes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAll());
    }

    @ApiOperation("DELETE like, dislike and article rating by composite key: Article_id, User_id")
    @DeleteMapping(value = "/article_id={article_id}/user_id={user_id}", produces = "application/json")
    public ResponseEntity deleteLike(@PathVariable(name = "article_id") Long articleId, @PathVariable(name = "user_id") Long userId) {
        service.delete(articleId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Like/Rating successfully removed");
    }
}
