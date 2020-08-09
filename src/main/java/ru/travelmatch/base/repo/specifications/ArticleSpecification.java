package ru.travelmatch.base.repo.specifications;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.*;
import ru.travelmatch.base.repo.filters.ArticleFilter;
import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Farida Gareeva
 * Created 01/07/2020
 * v1.0
 * Спецификация для отборов по сущности Article и его свойствам
 * Отборы производятся
 * 1. по полям сущности Article
 * 2. по списку тегов с условием И, т.е. в отбор должны попадать только
 * те статьи, у которых есть все теги, указанные в списке
 * 3. по количеству лайков и дизлайков (из таблицы ArticleLikeRating)
 * 4. по количеству выставленных оценок для рейтинга (из таблицы ArticleLikeRating)
 * 5. по рейтингу (среднее арифметическое оценок из таблицы ArticleLikeRating)
 * Сортировка производится по полям (с типом не List) Article. В случае ссылочных типов, сортировка по id.
 */

public class ArticleSpecification {
    private static final ArticleFilter.ArticleValue LIKE = ArticleFilter.ArticleValue.LIKE;
    private static final ArticleFilter.ArticleValue DISLIKE = ArticleFilter.ArticleValue.DISLIKE;
    private static final ArticleFilter.ArticleValue RATING_COUNT = ArticleFilter.ArticleValue.RATING_COUNT;
    private static final ArticleFilter.ArticleValue RATING_AVG = ArticleFilter.ArticleValue.RATING_AVG;
    public static final ArticleFilter.ComparisonOperations EQUAL = ArticleFilter.ComparisonOperations.EQUAL;
    public static final ArticleFilter.ComparisonOperations GREATER = ArticleFilter.ComparisonOperations.GREATER;
    public static final ArticleFilter.ComparisonOperations LESS = ArticleFilter.ComparisonOperations.LESS;

    /**
     * Метод, возвращает спецификацию для отбора статьи по ее id (поле category<{@link Article}>)
     *
     * @param idString    - строка, значение id, принятое от пользователя, должно конвертироваться в Long
     * @param errorJoiner - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> idEq(String idString, StringJoiner errorJoiner) {
        StringJoiner idError = new StringJoiner("");
        Long id = convertStringToLong(idString, idError);
        if (id == null) {
            errorJoiner.add("Invalid value of id: '" + idError.toString() + "' couldn't convert to Long");
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    /**
     * Метод, возвращает спецификацию для отбора статьи по id ее автора (поле category<{@link User}>)
     *
     * @param idString            - строка, значение id, принятое от пользователя, должно конвертироваться в Long
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> authorEq(String idString,
                                                  StringJoiner errorJoiner) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "author");
    }

    private static Specification<Article> getArticleSpecificationByIdOfObjectField(String idString,
                                                                                   StringJoiner errorJoiner,
                                                                                   String fieldName) {
        StringJoiner idError = new StringJoiner("");
        Long id = convertStringToLong(idString, idError);
        if (id == null) {
            errorJoiner.add("Invalid value of id: '" + idError.toString() + "' couldn't convert to Long");
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(fieldName).get("id"), id);
        };
    }

    /**
     * Метод заполняет список, по которому будет производиться сортировка выводимого результата
     *
     * @param orderAttributesList - список переданных полей для сортировки
     * @param root
     * @param criteriaQuery
     * @param criteriaBuilder
     */
    private static void addOrderBy(List<SingularAttribute> orderAttributesList,
                                   Sort.Direction directionSort,
                                   Root<Article> root,
                                   CriteriaQuery<?> criteriaQuery,
                                   CriteriaBuilder criteriaBuilder) {
        List<Order> orderList = new ArrayList<>(orderAttributesList.size());
        orderAttributesList.forEach(attr -> orderList.add(
                directionSort == Sort.Direction.ASC ?
                        criteriaBuilder.asc(root.get(attr)) :
                        criteriaBuilder.desc(root.get(attr))
                )
        );
        criteriaQuery.orderBy(orderList);
    }

    /**
     * Метод, возвращает спецификацию для отбора статьи по id ее категории (поле category<{@link ArticleCategory}>)
     *
     * @param idString            - строка, значение id, принятое от пользователя, должно конвертироваться в Long
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> categoryEq(String idString,
                                                    StringJoiner errorJoiner) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "category");
    }

    /**
     * Метод, возвращает спецификацию для отбора статьи по id ее города (поле category<{@link City}>)
     *
     * @param idString            - строка, значение id, принятое от пользователя, должно конвертироваться в Long
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> cityEq(String idString,
                                                StringJoiner errorJoiner) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "city");
    }

    /**
     * Метод, возвращает спецификацию для отбора статьи по id языка, на котором она написана (поле category<{@link Language}>)
     *
     * @param idString            - строка, значение id, принятое от пользователя, должно конвертироваться в Long
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> languageEq(String idString,
                                                    StringJoiner errorJoiner) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "language");
    }

    /**
     * Метод, возвращает спецификацию для отбора статьи по ее содержимому
     *
     * @param word                - строка поиска, регистр не важен, все приводится к нижнему при поиске
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> textContains(String word,
                                                      StringJoiner errorJoiner) {
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("text")), "%" + word.toLowerCase() + "%");
        };
    }

    /**
     * Метод, возвращает спецификацию для отбора статьи по содержимому в ее заголовке
     *
     * @param word                - строка поиска, регистр не важен, все приводится к нижнему при поиске
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> titleContains(String word,
                                                       StringJoiner errorJoiner) {
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + word.toLowerCase() + "%");
        };
    }

    /**
     * Метод возвращает спецификацию для отбора статьи по точному соответствию ее заголовка
     *
     * @param word                - строка поиска, регистр не важен, все приводится к нижнему при поиске
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> titleEq(String word,
                                                 StringJoiner errorJoiner) {
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("title")), word.toLowerCase());
        };
    }

    /**
     * Метод возвращает спецификацию для отбора статьи по точному соответствию
     * поля статьи с типом LocalDateTime переданному параметру
     *
     * @param dateString          - строковое значение даты, должно конвертироваться в LocalDateTime, например, 2020-04-04T23:59:59
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @param fieldName           - строковое название поля сущности {@link Article} c типом LocalDateTime
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> dateEq(String dateString,
                                                StringJoiner errorJoiner,
                                                String fieldName) {
        LocalDateTime date = convertStringToDateTime(dateString, errorJoiner, fieldName);
        if (date == null) {
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(fieldName), date);
        };
    }

    /**
     * Метод возвращает спецификацию для отбора статьи по значению
     * поля статьи с типом LocalDateTime с условием >= переданному параметру
     *
     * @param dateString          - строковое значение даты, должно конвертироваться в LocalDateTime, например, 2020-04-04T23:59:59
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @param fieldName           - строковое название поля сущности {@link Article} c типом LocalDateTime
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> dateGreaterThan(String dateString,
                                                         StringJoiner errorJoiner,
                                                         String fieldName) {
        LocalDateTime date = convertStringToDateTime(dateString, errorJoiner, fieldName);
        if (date == null) {
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get(fieldName), date);
        };
    }

    /**
     * Метод возвращает спецификацию для отбора статьи по значению
     * поля статьи с типом LocalDateTime с условием <= переданному параметру
     *
     * @param dateString          - строковое значение даты, должно конвертироваться в LocalDateTime, например, 2020-04-04T23:59:59
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @param fieldName           - строковое название поля сущности {@link Article} c типом LocalDateTime
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> dateLessThan(String dateString,
                                                      StringJoiner errorJoiner,
                                                      String fieldName) {
        LocalDateTime date = convertStringToDateTime(dateString, errorJoiner, fieldName);
        if (date == null) {
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThan(root.get(fieldName), date);
        };
    }

    private static LocalDateTime convertStringToDateTime(String dateString,
                                                         StringJoiner errorJoiner,
                                                         String fieldName) {
        LocalDateTime date = null;
        try {
            date = LocalDateTime.parse(dateString);
        } catch (DateTimeParseException ex) {
            errorJoiner.add("Invalid value of'" + fieldName + "' = "
                    + dateString + ". Couldn't convert to LocalDateTime. "
                    + ex.getMessage());
        }
        return date;
    }

    private static Long convertStringToLong(String idString, StringJoiner errorList) {
        try {
            return Long.parseLong(idString);
        } catch (NumberFormatException ex) {
            errorList.add(idString);
        }
        return null;
    }

    /**
     * Метод возвращает спецификацию для отбора статей, которые удовлетворяют заданным условиям.
     *
     * @param countLikesString    - строковое представление числового параметра, должно конвертироваться в Long
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @param articleValue        - представление числового оценки статьи. Возможные значения:
     *                            LIKE - если требуется посчитать лайки.
     *                            В этом случае вычисляется количество строк в таблице {@link ArticleLikeRating}
     *                            по полю likeDislike = 1.
     *                            DISLIKE - если требуется посчитать дизлайки;
     *                            В этом случае вычисляется количество строк в таблице {@link ArticleLikeRating}
     *                            по полю likeDislike = -1.
     *                            RATING_COUNT - если требуется посчитать количество оценок, поставленных
     *                            статье, из которых вычисляется рейтинг статьи - средняя оценка;
     *                            В этом случае вычисляется количество строк в таблице {@link ArticleLikeRating},
     *                            у которых поле rating заполнено и отлично от int 0.
     *                            RATING_AVG - если требуется вычислить рейтинг статьи.
     *                            В этом случае вычисляется средняя оценка в таблице {@link ArticleLikeRating}
     *                            по полю rating. В расчет принимаются только значения от 1 до 5.
     * @param comparisonString    - представление сравнения. Возможные значения: EQUAL, GREATER, LESS.
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> likesDislikesRatingsComparison(String countLikesString,
                                                                        StringJoiner errorJoiner,
                                                                        ArticleFilter.ArticleValue articleValue,
                                                                        ArticleFilter.ComparisonOperations comparisonString) {
        //проверка и преобразование строки countLikes в Long (не int, так как вычисляем сумму по таблице, где Long id)
        StringJoiner countError = new StringJoiner("");
        Long countLikes = convertStringToLong(countLikesString, countError);
        if (countLikes == null) {
            errorJoiner.add("Invalid value of count '" + articleValue.getTitle() + ": '"
                    + countError.toString() + "' couldn't convert to Long");
            return Specification.where(null);
        }

        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Article, ArticleLikeRating> likesJoin;
            //Поищем, нет ли уже в запросе join с таблицей ArticleLike.
            //Если есть, то второй join добавлять нельзя, так как выдается неверный результат
            List likeJoinList = root.getJoins()
                    .stream()
                    .filter(articleJoin -> articleJoin.getJavaType() == ArticleLikeRating.class)
                    .collect(Collectors.toList());

            if (likeJoinList.size() == 0) {
                //join не обнаружен, создадим его
                likesJoin = root.join("likes", JoinType.LEFT);
            } else {
                //иначе вытянем готовый join
                likesJoin = (Join<Article, ArticleLikeRating>) likeJoinList.get(0);
            }

            Coalesce coalesceLike = criteriaBuilder.coalesce();
            coalesceLike.value(likesJoin.get(articleValue.getPath()));
            coalesceLike.value(0);

            Expression<Long> likeCountExpression = criteriaBuilder.<Long>selectCase()
                    .when(criteriaBuilder.equal(coalesceLike, 1), 1L)
                    .otherwise(0L);

            Expression<Long> dislikeCountExpression = criteriaBuilder.<Long>selectCase()
                    .when(criteriaBuilder.equal(coalesceLike, -1), 1L)
                    .otherwise(0L);

            Expression<Long> ratingValueCountExpression = criteriaBuilder.<Long>selectCase()
                    .when(criteriaBuilder.isNotNull(likesJoin.get(RATING_COUNT.getPath())), 1L)
                    .otherwise(criteriaBuilder.nullLiteral(Long.class));

            Expression<Long> ratingAvgExpression = likesJoin.get(RATING_AVG.getPath());

            Expression<Long> actualLongExpression = null;
            Expression<Double> actualDoubleExpression = null;
            switch (articleValue) {
                case LIKE:
                    actualLongExpression = criteriaBuilder.sum(likeCountExpression);
                    break;
                case DISLIKE:
                    actualLongExpression = criteriaBuilder.sum(dislikeCountExpression);
                    break;
                case RATING_COUNT:
                    actualLongExpression = criteriaBuilder.sum(ratingValueCountExpression);
                    break;
                case RATING_AVG:
                    actualDoubleExpression = criteriaBuilder.avg(ratingAvgExpression);
                    break;
                default:
                    errorJoiner.add("Illegal argument of method likesDislikesComparison in ArticleSpecification.class." +
                            " Invalid value of ArticleFilter.ArticleValue argument. Call programmer for help.");
            }

            criteriaQuery
                    //  .distinct(true)
                    .groupBy(root.get(Article_.id));

            Expression actualExpression = articleValue.equals(RATING_AVG) ? actualDoubleExpression : actualLongExpression;
            Predicate newHaving = null;
            if (comparisonString == GREATER) {
                newHaving = criteriaBuilder.greaterThanOrEqualTo(actualExpression, countLikes);
            } else if (comparisonString == LESS) {
                newHaving = criteriaBuilder.lessThanOrEqualTo(actualExpression, countLikes);
            } else if (comparisonString == EQUAL) {
                newHaving = criteriaBuilder.equal(actualExpression, countLikes);
            } else {
                errorJoiner.add("Illegal argument of method likesDislikesComparison in ArticleSpecification.class." +
                        " Invalid value of ArticleFilter.ComparisonOperations argument. Call programmer for help.");
            }

            //Вытягиваем существующие условия having.
            //Если просто добавить новые условия, то существующие затрутся.
            Predicate existingRestrictions = criteriaQuery.getGroupRestriction();
            List<Predicate> predicates = new ArrayList<>();
            if (existingRestrictions != null) {
                predicates.add(existingRestrictions);
            }
            predicates.add(newHaving);

            criteriaQuery.having(predicates.toArray(new Predicate[]{}));
            return criteriaQuery.getRestriction();
        };
    }

    /**
     * Метод возвращает спецификацию для отбора статей, к которым подвязаны все теги из списка.
     * Чтобы выбрать такие статьи, соединяем таблицы articles и articles_tags внутренним соединением
     * и добавляем условие, что articles_tags.tag_id in (<список тегов>) AND количество отобранных строк
     * по каждой статье должно быть равно длине списка тегов.
     * Если пользователь в рамках данного http-запроса ставил условие не только на список тегов, но и на лайки
     * и/или дизлайки и/или рейтинги, то запрос по тегам помещается во вложенный. Далее производится выборка
     * по лайкам/дизлайкам/рейтингам и добавляется дополнительное условие, что id статьи должен быть в списке
     * статей, удовлетворяющих вложенному запросу по тегам.
     *
     * @param concatTags          - строка со списком id тегов, разделенных запятой. Например, concatTags = 1,5,14
     *                            каждый элемент списка должен приводиться к типу int
     * @param errorJoiner         - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возращает подготовленную спецификацию.
     */
    public static Specification<Article> allTagIdIn(String concatTags,
                                                    StringJoiner errorJoiner) {
        StringJoiner invalidLongJoiner = new StringJoiner(",");
        List<Long> arrayTags = Stream.of(concatTags.split(","))
                .map(idString -> convertStringToLong(idString, invalidLongJoiner))
                .collect(Collectors.toList());

        if (invalidLongJoiner.length() != 0) {
            errorJoiner.add("Invalid values of id " + invalidLongJoiner.toString() + " couldn't convert to Long");
            return Specification.where(null);
        }

        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            //проверим наличие других join.
            //если других нет, то все прекрасно, запрос будет простым.
            //если есть другие join, придется помучиться и создать вложенный запрос.
            if (root.getJoins().isEmpty()) {//ура! все просто, страдать не придется!
                Join<Article, Tag> tagJoin = root.join("tags", JoinType.INNER);
                tagJoin.on(criteriaBuilder.in(tagJoin.get("id")).value(arrayTags));
                criteriaQuery
                        .groupBy(root.get(Article_.id))
                        .having(criteriaBuilder.equal(criteriaBuilder.count(root), arrayTags.size()))
                        .distinct(true);
                return criteriaQuery.getRestriction();
            } else {//придется помучиться с вложенным запросом
                Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
                Root<Article> subRoot = subquery.from(Article.class);
                Join<Article, Tag> tagJoin = subRoot.join("tags", JoinType.INNER);
                tagJoin.on(criteriaBuilder.in(tagJoin.get("id")).value(arrayTags));
                subquery.select(subRoot.get(Article_.id))
                        .groupBy(subRoot.get(Article_.id))
                        .having(criteriaBuilder.equal(criteriaBuilder.count(subRoot), arrayTags.size()))
                        .distinct(true);

                //теперь подготовленный вложенный запрос втолкнем в существующий запрос
                //для этого достаточно добавить условие where
                Predicate newRestriction = criteriaBuilder.in(root.get(Article_.id)).value(subquery);
                //Если просто добавить новые условия, то существующие затрутся.
                Predicate existingRestrictions = criteriaQuery.getRestriction();
                List<Predicate> predicates = new ArrayList<>();
                if (existingRestrictions != null) {
                    predicates.add(existingRestrictions);
                }
                predicates.add(newRestriction);

                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery.getRestriction();
            }
        };
    }

    /**
     *
     * @param concatProperties - String, список полей для сортировки, регистр не важен.
     *                         Сортировать можно по полям сущности {@link Article}, с типом не List:
     *                         id,title,text,category,author,created,lastUpdated,city,language
     *                         Причем сортировка по ссылочным типам (н-р, Category, City) производится по id
     * @param directionSort - направление сортировки
     * @param errorJoiner - Joiner, собирает все некорректные данные, полученные от пользователя, в рамках одного http-запроса
     * @return возвращает заполненную спецификацию
     */
    public static Specification<Article> addOrderByProperties(String concatProperties,
                                                              Sort.Direction directionSort,
                                                              StringJoiner errorJoiner) {

        //получим таблицу соответствия имени String и SingularAttribute
        Map<String, SingularAttribute> enablePropertiesMap = Article_.getAttributeMap();
        List<SingularAttribute> orderList = new ArrayList<>();
        Stream.of(concatProperties.toLowerCase().split(","))
                .forEach(property -> {
                    if (enablePropertiesMap.get(property) != null) {
                        orderList.add(enablePropertiesMap.get(property));
                    } else {
                        errorJoiner.add("Invalid field '" + property + "' for ordering");
                    }
                });
        if (errorJoiner.length() > 0) {
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderList, directionSort, root, criteriaQuery, criteriaBuilder);
            return criteriaQuery.getRestriction();
        };
    }
}
