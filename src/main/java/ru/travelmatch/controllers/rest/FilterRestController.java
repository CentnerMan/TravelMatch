package ru.travelmatch.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Api("Set of endpoints for select operations.")
public class FilterRestController {

    private UserService userService;

    private ArticleService articleService;

    @Autowired
    public FilterRestController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("users")
    @ApiOperation("Return list of Users, selected by some conditions. DON'T TEST! HAS ERRORS!")
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

    /**
     * Метод возвращает список статей в соответствии с заданными фильтрами
     *
     * @param request                           - HttpServletRequest запрос
     * @param response                          - HttpServletResponse ответ
//     * @param page_number                       - Integer, номер страницы, отсчет с 1. Значение по умолчанию 1.
//     * @param page_size                         - Integer, количество элементов на странице, значение по умолчанию = 20
//     * @param order_direction                   - String, направление сортировки, возможные значения ASC и DESC. Значение по умолчанию ASC. регистронезависимые
//     * @param order_by_properties               - String, через запятую перечисляются поля класса Article (с типом не List) для сортировки
//     * @param id                                - Long, id статьи, полe, поиск на точное соответствие
//     * @param author_id                         - Long, id пользователя {@link ru.travelmatch.base.entities.User}, связанная сущность ManyToOne,
//     *                                          поиск на точное соответствие
//     * @param category_id                       - Long, id категории статьи {@link ru.travelmatch.base.entities.ArticleCategory},
//     *                                          связанная сущность ManyToOne, поиск на точное соответствие
//     * @param city_id                           - Long, id города {@link ru.travelmatch.base.entities.City}, связанная сущность ManyToOne,
    //     *                                          поиск на точное соответствие
    //     * @param language_id                       - Long, id языка народ мира {@link ru.travelmatch.base.entities.Language}, связанная сущность
    //     *                                          ManyToOne, поиск на точное соответствие
    //     * @param text                              - String, text, поле, регистронезависимый поиск по вхождению фразы в текст статьи
    //     * @param title_equal                       - String, title, поле, регистронезависимый поиск на точное соответствие заголовка статьи
    //     * @param title_contains                    - String, title, поле, регистронезависимый поиск на вхождение фразы в заголовок статьи
    //     * @param created_equal                     - LocalDateTime, created, поле, поиск на точное соответствие по дате создания статьи (н-р, 2020-03-03T00:00:00)
    //     * @param created_before                    - LocalDateTime, created, поле, поиск статей, созданных раньше или точно в эту же дату (н-р, 2020-03-03T00:00:00)
    //     * @param created_after                     - LocalDateTime, created, поле, поиск статей, созданных после или точно в эту же дату (н-р, 2020-03-03T00:00:00)
    //     * @param updated_equal                     - LocalDateTime, lastUpdated, поле, поиск на точное соответствие по дате последнего обновления статьи статьи (н-р, 2020-03-03T00:00:00)
    //     * @param updated_before                    - LocalDateTime, lastUpdated, поле, поиск статей, дате последнего обновления которых раньше или точно в эту же дату (н-р, 2020-03-03T00:00:00)
    //     * @param updated_after                     - LocalDateTime, lastUpdated, поле, поиск статей, дате последнего обновления после или точно в эту же дату (н-р, 2020-03-03T00:00:00)
    //     * @param likes_equal                       - Long, количество лайков на точное равенство, вычисляется по полю likeDislike = 1 из таблицы
    //     *                                          лайков и рейтингов {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany
    //     * @param likes_greaterOrEqual              - Long, количество лайков, большее либо равное указанному значению,
    //     *                                          вычисляется как количество строк, где likeDislike = 1 из таблицы лайков и рейтингов
    //     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany
    //     * @param likes_lessOrEqual                 - Long, количество лайков, меньшее либо равное указанному значению,
    //     *                                          вычисляется как количество строк, где likeDislike = 1 из таблицы лайков и рейтингов
    //     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany
    //     * @param dislikes_equal                    - Long, количество дизлайков на точное равенство, вычисляется как количество строк,
    //     *                                          где likeDislike = -1 из таблицы лайков и рейтингов
//     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany
//     * @param dislikes_greaterOrEqual           - Long, количество дизлайков, большее либо равное указанному значению,
//     *                                          вычисляется как количество строк, где likeDislike = -1 из таблицы лайков и рейтингов
//     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany
//     * @param dislikes_lessOrEqual              - Long, количество дизлайков, меньшее либо равное указанному значению,
//     *                                          вычисляется как количество строк, где likeDislike = -1 из таблицы лайков и рейтингов
//     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany
//     * @param rating_value_count_equal          - Long, количество полученных оценок, отличных от 0 и null, на точное равенство,
//     *                                          вычисляется как количество строк, где rating не null и не 0, из таблицы лайков и рейтингов
//     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany
//     * @param rating_value_count_greaterOrEqual - Long, количество полученных оценок, отличных от 0 и null, большее либо равное указанному
//     *                                          значению, вычисляется как количество строк, где rating не null и не 0, из таблицы лайков
//     *                                          и рейтингов {@link ru.travelmatch.base.entities.ArticleLikeRating},
//     *                                          связанная сущность OneToMany
//     * @param rating_value_count_lessOrEqual    - Long, количество полученных оценок, отличных от 0 и null, меньшее либо равное указанному
//     *                                          значению, вычисляется как количество строк, где rating не null и не 0, из таблицы лайков
//     *                                          и рейтингов {@link ru.travelmatch.base.entities.ArticleLikeRating},
//     *                                          связанная сущность OneToMany
//     * @param rating_equal                      - Long, средняя оценка на точное соответствие,
//     *                                          вычисляется как среднее по полю rating из таблицы лайков и рейтингов
//     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating},связанная сущность OneToMany.
//     *                                          В расчет принимаются только значения, отличные от null и 0.
//     * @param rating_greaterOrEqual             - Long, средняя оценка на точное соответствие,
//     *                                          вычисляется как среднее по полю rating из таблицы лайков и рейтингов
//     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating},связанная сущность OneToMany.
//     *                                          В расчет принимаются только значения, отличные от null и 0.
//     * @param rating_lessOrEqual                - Long, средняя оценка, меньшая либо равная указанному значению,
//     *                                          вычисляется как среднее по полю rating из таблицы лайков и рейтингов
//     *                                          {@link ru.travelmatch.base.entities.ArticleLikeRating},связанная сущность OneToMany.
//     *                                          В расчет принимаются только значения, отличные от null и 0.
//     * @param tags_id                           - String, список id тегов {@link ru.travelmatch.base.entities.Tag}, которые должны быть подвязаны к статье.
     *                                          Причем в отбор должны попадать лишь те статьи, к которым подвязан весь список тегов.
     * @return
     */
    @GetMapping("articles")
    @ApiOperation("Return list of Articles, selected by some conditions")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_number",
                    value = "Integer, номер страницы, отсчет с 1. Значение по умолчанию 1",
                    defaultValue = "1", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "page_size",
                    value = "Integer, количество элементов на странице, значение по умолчанию = 20",
                    defaultValue = "20", required = false, dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "order_direction",
                    value = "String, направление сортировки, возможные значения ASC и DESC. Значение по умолчанию ASC. регистронезависимые",
                    defaultValue = "ASC", required = false, dataTypeClass = String.class, allowEmptyValue = false),
            @ApiImplicitParam(name = "order_by_properties",
                    value = "String, список полей для сортировки, регистр не важен.\n" +
                            "Сортировать можно по полям сущности {@link Article}, с типом не List:\n" +
                            "id,title,text,category,author,created,lastUpdated,city,language\n" +
                            "Причем сортировка по ссылочным типам (н-р, Category, City) производится по id", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "id",
                    value = "Long, id статьи, полe, поиск на точное соответствие", required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "author_id",
                    value = "Long, id пользователя {@link ru.travelmatch.base.entities.User}, связанная сущность ManyToOne,\n" +
                            "поиск на точное соответствие", required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "category_id",
                    value = "Long, id категории статьи {@link ru.travelmatch.base.entities.ArticleCategory},\n" +
                            "связанная сущность ManyToOne, поиск на точное соответствие", required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "city_id",
                    value = "Long, id города {@link ru.travelmatch.base.entities.City}, связанная сущность ManyToOne,\n" +
                            "поиск на точное соответствие", required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "language_id",
                    value = "Long, id языка народ мира {@link ru.travelmatch.base.entities.Language}, связанная сущность\n" +
                            "ManyToOne, поиск на точное соответствие", required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "text",
                    value = "String, text, поле, регистронезависимый поиск по вхождению фразы в текст статьи", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "title_equal",
                    value = "String, title, поле, регистронезависимый поиск на точное соответствие заголовка статьи", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "title_contains",
                    value = "String, title, поле, регистронезависимый поиск на вхождение фразы в заголовок статьи", required = false, dataTypeClass = String.class),
            @ApiImplicitParam(name = "created_equal",
                    value = "LocalDateTime, created, поле, поиск на точное соответствие по дате создания статьи (н-р, 2020-03-03T00:00:00)", required = false, dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "created_before",
                    value = "LocalDateTime, created, поле, поиск статей, созданных раньше или точно в эту же дату (н-р, 2020-03-03T00:00:00)", required = false, dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "created_after",
                    value = "LocalDateTime, created, поле, поиск статей, созданных после или точно в эту же дату (н-р, 2020-03-03T00:00:00)", required = false, dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "updated_equal",
                    value = "LocalDateTime, lastUpdated, поле, поиск на точное соответствие по дате последнего обновления статьи статьи (н-р, 2020-03-03T00:00:00)",
                    required = false, dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "updated_before",
                    value = "LocalDateTime, lastUpdated, поле, поиск статей по дате последнего обновления которых раньше или точно в эту же дату (н-р, 2020-03-03T00:00:00)",
                    required = false, dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "updated_after",
                    value = "LocalDateTime, lastUpdated, поле, поиск статей по дате последнего обновления после или точно в эту же дату (н-р, 2020-03-03T00:00:00)",
                    required = false, dataTypeClass = LocalDateTime.class),
            @ApiImplicitParam(name = "likes_equal",
                    value = "Long, количество лайков на точное равенство, вычисляется как количество строк, где likeDislike = 1 из таблицы\n" +
                            "лайков и рейтингов {@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "likes_greaterOrEqual",
                    value = "Long, количество лайков, большее либо равное указанному значению,\n" +
                            "вычисляется как количество строк, где likeDislike = 1 из таблицы лайков и рейтингов,\n"+
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "likes_lessOrEqual",
                    value = "Long, количество лайков, меньшее либо равное указанному значению,\n" +
                            "вычисляется как количество строк, где likeDislike = 1 из таблицы лайков и рейтингов,\n"+
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "dislikes_equal",
                    value = "Long, количество дизлайков на точное равенство, вычисляется как количество строк,\n" +
                            "где likeDislike = -1 из таблицы лайков и рейтингов,\n"+
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "dislikes_greaterOrEqual",
                    value = "Long, количество дизлайков, большее либо равное указанному значению,\n" +
                            "вычисляется как количество строк, где likeDislike = -1 из таблицы лайков и рейтингов,\n"+
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "dislikes_lessOrEqual",
                    value = "Long, количество дизлайков, меньшее либо равное указанному значению,\n" +
                            "вычисляется как количество строк, где likeDislike = -1 из таблицы лайков и рейтингов\n" +
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "rating_value_count_equal",
                    value = "Long, количество полученных оценок, отличных от 0 и null, на точное равенство,\n" +
                            "вычисляется как количество строк, где rating не null и не 0, из таблицы лайков и рейтингов\n" +
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating}, связанная сущность OneToMany\n",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "rating_value_count_greaterOrEqual",
                    value = "Long, количество полученных оценок, отличных от 0 и null, большее либо равное указанному\n" +
                            "значению, вычисляется как количество строк, где rating не null и не 0, из таблицы лайков\n" +
                            "и рейтингов {@link ru.travelmatch.base.entities.ArticleLikeRating},\n" +
                            "связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "rating_value_count_lessOrEqual",
                    value = "Long, количество полученных оценок, отличных от 0 и null, меньшее либо равное указанному\n" +
                            "значению, вычисляется как количество строк, где rating не null и не 0, из таблицы лайков\n" +
                            "и рейтингов {@link ru.travelmatch.base.entities.ArticleLikeRating},\n" +
                            "связанная сущность OneToMany",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "rating_equal",
                    value = "Long, средняя оценка на точное соответствие,\n" +
                            "вычисляется как среднее по полю rating из таблицы лайков и рейтингов\n" +
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating},связанная сущность OneToMany.\n" +
                            "В расчет принимаются только значения, отличные от null и 0.",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "rating_greaterOrEqual",
                    value = "Long, средняя оценка на точное соответствие,\n" +
                            "вычисляется как среднее по полю rating из таблицы лайков и рейтингов\n" +
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating},связанная сущность OneToMany.\n" +
                            "В расчет принимаются только значения, отличные от null и 0.",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "rating_lessOrEqual",
                    value = "Long, средняя оценка, меньшая либо равная указанному значению,\n" +
                            "вычисляется как среднее по полю rating из таблицы лайков и рейтингов\n" +
                            "{@link ru.travelmatch.base.entities.ArticleLikeRating},связанная сущность OneToMany.\n" +
                            "В расчет принимаются только значения, отличные от null и 0.",
                    required = false, dataTypeClass = Long.class),
            @ApiImplicitParam(name = "tags_id",
                    value = "String, список id тегов {@link ru.travelmatch.base.entities.Tag}, которые должны быть подвязаны к статье.\n" +
                            "Причем в отбор должны попадать лишь те статьи, к которым подвязан весь список тегов.",
                    required = false, dataTypeClass = String.class)
    }
    )
    public ResponseEntity<List<ArticleSimpleDTO>> getSomeArticles(HttpServletRequest request, HttpServletResponse response) {
        StringJoiner errorJoiner = new StringJoiner("; ");
        ArticleFilter filter = new ArticleFilter(request, errorJoiner);
        if (errorJoiner.length() != 0) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, errorJoiner.toString());
                return ResponseEntity.status(response.getStatus()).body(new ArrayList<>());
            } catch (IOException e) {
                throw new IllegalArgumentException(errorJoiner.toString());
            }
        }
        return ResponseEntity.status(response.getStatus())
                .body(articleService.findAll(filter.getSpecification(), PageRequest.of(filter.getPageNumber() - 1, filter.getPageSize()))
                        .stream()
                        .map(ArticleSimpleDTO::new)
                        .collect(Collectors.toList()));
    }

}
