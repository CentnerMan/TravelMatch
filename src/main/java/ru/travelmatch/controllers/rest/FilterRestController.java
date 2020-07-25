package ru.travelmatch.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.travelmatch.base.repo.filters.ArticleFilter;
import ru.travelmatch.base.repo.filters.UserFilter;
import ru.travelmatch.dto.ArticleSimpleDTO;
import ru.travelmatch.dto.UserSimpleDTO;
import ru.travelmatch.services.ArticleService;
import ru.travelmatch.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 01/07/2020
 * v1.0
 * REST контроллер для отборов
 */

@RestController
@RequestMapping("api/v1/filter")
public class FilterRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("users")
    public ResponseEntity<List<UserSimpleDTO>> getSomeUsers(HttpServletRequest request, HttpServletResponse response) {
        StringJoiner errorJoiner = new StringJoiner("; ");
        UserFilter filter = new UserFilter(request, errorJoiner);
        if (errorJoiner.length() != 0) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorJoiner.toString());
            } catch (IOException e) {
                throw new IllegalArgumentException(errorJoiner.toString());
            }
        }
        return ResponseEntity.status(response.getStatus())
                .body(userService.findAll(filter.getSpecification())
                        .stream()
                        .map(UserSimpleDTO::new)
                        .collect(Collectors.toList()));
    }

    @GetMapping("articles")
    public ResponseEntity<List<ArticleSimpleDTO>> getSomeArticles(HttpServletRequest request, HttpServletResponse response){
        StringJoiner errorJoiner = new StringJoiner("; ");
        ArticleFilter filter = new ArticleFilter(request, errorJoiner);
        if (errorJoiner.length() != 0) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorJoiner.toString());
            } catch (IOException e) {
                throw new IllegalArgumentException(errorJoiner.toString());
            }
        }
        return ResponseEntity.status(response.getStatus())
                .body(articleService.findAll(filter.getSpecification())
                        .stream()
                        .map(ArticleSimpleDTO::new)
                        .collect(Collectors.toList()));
    }

}
