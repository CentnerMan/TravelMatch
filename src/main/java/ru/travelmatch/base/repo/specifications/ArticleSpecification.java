package ru.travelmatch.base.repo.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.*;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
 * 3. по количеству лайков и дизлайков
 * 4. по количеству выставленных оценок для рейтинга (из таблицы ArticleRating)
 * 5. по рейтингу (среднее арифметическое оценок из таблицы ArticleRating)
 */

public class ArticleSpecification {

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

    public static Specification<Article> authorEq(String idString, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "author", orderAttributesList);
    }

    private static Specification<Article> getArticleSpecificationByIdOfObjectField(String idString, StringJoiner errorJoiner, String fieldName, List<SingularAttribute> orderAttributesList) {
        StringJoiner idError = new StringJoiner("");
        Long id = convertStringToLong(idString, idError);
        if (id == null) {
            errorJoiner.add("Invalid value of id: '" + idError.toString() + "' couldn't convert to Long");
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.equal(root.get(fieldName).get("id"), id);
        };
    }

    private static void addOrderBy(List<SingularAttribute> orderAttributesList, Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Order> orderList = new ArrayList<>(orderAttributesList.size());
        orderAttributesList.forEach(attr -> orderList.add(criteriaBuilder.desc(root.get(attr))));
        criteriaQuery.orderBy(orderList);
    }

    public static Specification<Article> categoryEq(String idString, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "category", orderAttributesList);
    }

    public static Specification<Article> cityEq(String idString, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "city", orderAttributesList);
    }

    public static Specification<Article> languageEq(String idString, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        return getArticleSpecificationByIdOfObjectField(idString, errorJoiner, "language", orderAttributesList);
    }

    public static Specification<Article> textContains(String word, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.like(root.get("text"), "%" + word + "%");
        };
    }

    public static Specification<Article> titleContains(String word, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.like(root.get("title"), "%" + word + "%");
        };
    }

    public static Specification<Article> titleEq(String word, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.equal(root.get("title"), word);
        };
    }

    public static Specification<Article> dateEq(String dateString, StringJoiner errorJoiner, String fieldName, List<SingularAttribute> orderAttributesList) {
        LocalDateTime date = convertStringToDateTime(dateString, errorJoiner, fieldName);
        if (date == null) {
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.equal(root.get(fieldName), date);
        };
    }

    public static Specification<Article> dateGreaterThan(String dateString, StringJoiner errorJoiner, String fieldName, List<SingularAttribute> orderAttributesList) {
        LocalDateTime date = convertStringToDateTime(dateString, errorJoiner, fieldName);
        if (date == null) {
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.greaterThan(root.get(fieldName), date);
        };
    }

    public static Specification<Article> dateLessThan(String dateString, StringJoiner errorJoiner, String fieldName, List<SingularAttribute> orderAttributesList) {
        LocalDateTime date = convertStringToDateTime(dateString, errorJoiner, fieldName);
        if (date == null) {
            return Specification.where(null);
        }
        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaBuilder.lessThan(root.get(fieldName), date);
        };
    }

    private static LocalDateTime convertStringToDateTime(String dateString, StringJoiner errorJoiner, String fieldName) {
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

    private static int convertStringToInt(String ageMinString, StringJoiner errorJoiner) {
        int age = -1;
        try {
            age = Integer.parseInt(ageMinString);
        } catch (NumberFormatException ex) {
            errorJoiner.add("Value '" + ageMinString + "' could't convert to Integer");
        }
        return age;
    }

    private static Long convertStringToLong(String idString, StringJoiner errorList) {
        try {
            return Long.parseLong(idString);
        } catch (NumberFormatException ex) {
            errorList.add(idString);
        }
        return null;
    }

    public static Specification<Article> allTagIdIn(String concatTags, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList) {
        StringJoiner invalidLongJoiner = new StringJoiner(",");
        List<Long> arrayTags = Stream.of(concatTags.split(","))
                .map(idString -> convertStringToLong(idString, invalidLongJoiner))
                .collect(Collectors.toList());

        if (invalidLongJoiner.length() != 0) {
            errorJoiner.add("Invalid values of id " + invalidLongJoiner.toString() + " couldn't convert to Long");
            return Specification.where(null);
        }

        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Article, Tag> tagJoin = root.join("tags", JoinType.INNER);
            tagJoin.on(criteriaBuilder.in(tagJoin.get("id")).value(arrayTags));
            criteriaQuery
                    .groupBy(root.get(Article_.id))
                    .having(criteriaBuilder.equal(criteriaBuilder.count(root), arrayTags.size()))
                    .distinct(true);
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaQuery.getRestriction();
        };
    }

    public static Specification<Article> likesDislikesComparison(String countLikesString, StringJoiner errorJoiner, List<SingularAttribute> orderAttributesList, int likeOrDislike, String comparisonString) {
        //проверка и преобразование строки countLikes в Long (не int, так как вычисляем сумму по таблице, где Long id)
        StringJoiner countError = new StringJoiner("");
        Long countLikes = convertStringToLong(countLikesString, countError);
        if (countLikes == null) {
            errorJoiner.add("Invalid value of count "
                    + (likeOrDislike == 1 ? "likes" : "dislikes")
                    + ": '" + countError.toString() + "' couldn't convert to Long");
            return Specification.where(null);
        }

        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
            Join<Article, ArticleLike> likesJoin;
            //Поищем, нет ли уже в запросе join с таблицей ArticleLike.
            //Если есть, то второй join добавлять нельзя, так как выдается неверный результат
            List likeJoinList = root.getJoins()
                    .stream()
                    .filter(articleJoin -> articleJoin.getJavaType() == ArticleLike.class)
                    .collect(Collectors.toList());

            if (likeJoinList.size() == 0) {
                //если join не обнаружен, создадим его
                likesJoin = root.join("likes", JoinType.LEFT);
            } else {
                //иначе вытянем готовый join
                likesJoin = (Join<Article, ArticleLike>) likeJoinList.get(0);
            }

            Coalesce coalesce = criteriaBuilder.coalesce();
            coalesce.value(likesJoin.get("value"));
            coalesce.value(0);

            Expression<Long> likeCountExpression = criteriaBuilder.<Long>selectCase()
                    .when(criteriaBuilder.equal(coalesce, 1), 1L)
                    .otherwise(0L);
            Expression<Long> dislikeCountExpression = criteriaBuilder.<Long>selectCase()
                    .when(criteriaBuilder.equal(coalesce, -1), 1L)
                    .otherwise(0L);

            criteriaQuery
                    .distinct(true)
                    .groupBy(root.get(Article_.id));
            Predicate newHaving = null;
            if (comparisonString.equals(">=")) {
                newHaving = criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.sum(likeOrDislike == 1 ? likeCountExpression : dislikeCountExpression), countLikes);
            } else if (comparisonString.equals("<=")) {
                newHaving = criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.sum(likeOrDislike == 1 ? likeCountExpression : dislikeCountExpression), countLikes);
            } else if (comparisonString.equals("=")) {
                newHaving = criteriaBuilder.equal(criteriaBuilder.sum(likeOrDislike == 1 ? likeCountExpression : dislikeCountExpression), countLikes);
            } else {
                errorJoiner.add("Illegal argument of method likesDislikesComparison in ArticleSpecification.class." +
                        " Call programmer for help.");
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
            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
            return criteriaQuery.getRestriction();
        };
    }

    public static Specification<Article> ratingValueCountComparison(String countString,
                                                                    StringJoiner errorJoiner,
                                                                    List<SingularAttribute> orderAttributesList,
                                                                    String comparisonString,
                                                                    String averageOrCount) {
        //проверка и преобразование строки countLikes в Long (не int, так как вычисляем сумму по таблице, где Long id)
        StringJoiner countError = new StringJoiner("");
        Long count = convertStringToLong(countString, countError);
        if (count == null) {
            errorJoiner.add("Invalid value of count values for rating: '" + countError.toString() + "' couldn't convert to Long");
            return Specification.where(null);
        }

        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {

            Join<Article, ArticleRating> ratingJoin;

            //Поищем, нет ли уже в запросе join с таблицей ArticleRating.
            //Если есть, то второй join добавлять нельзя, так как выдается неверный результат
            List ratingJoinList = root.getJoins()
                    .stream()
                    .filter(articleJoin -> articleJoin.getJavaType() == ArticleRating.class)
                    .collect(Collectors.toList());

            if (ratingJoinList.size() == 0) {
                //если join не обнаружен, создадим его
                ratingJoin = root.join("ratings", JoinType.LEFT);
                criteriaQuery
                        .distinct(true)
                        .groupBy(
                                ratingJoin.get("article"),
                                root.get(Article_.id));
            } else {
                //если join обнаружим, вытягиваем готовый
                ratingJoin = (Join<Article, ArticleRating>) ratingJoinList.get(0);
            }

            //Формируем новое условие having в зависимости от переданного параметра >=,<=,=
            //и значения averageOrCount - принимает значения "average"  и "count",
            //для подсчета средней оценки или количества оценок соответственно.
            Predicate havingConditionPredicate = null;
            Expression<? extends Long> ratingCountExpression = null;
            Expression<? extends Double> ratingExpression = null;
            if (averageOrCount.equals("count")) {
                ratingCountExpression = criteriaBuilder.sum(
                        criteriaBuilder.<Long>selectCase()
                                .when(criteriaBuilder.isNull(ratingJoin.get("article")), 0L)
                                .otherwise(1L)
                );
                if (comparisonString.equals("<=")) {
                    havingConditionPredicate = criteriaBuilder.lessThanOrEqualTo(ratingCountExpression, count);
                } else if (comparisonString.equals(">=")) {
                    havingConditionPredicate = criteriaBuilder.greaterThanOrEqualTo(ratingCountExpression, count);
                } else if (comparisonString.equals("=")) {
                    havingConditionPredicate = criteriaBuilder.equal(ratingCountExpression, count);
                } else {
                    errorJoiner.add("Illegal argument of method ratingValueCountComparison in ArticleSpecification.class." +
                            " Call programmer for help.");
                }
            } else if (averageOrCount.equals("average")) {
                Coalesce<Integer> coalesce = criteriaBuilder.coalesce();
                coalesce.value(ratingJoin.get("value"));
                coalesce.value(0);
                ratingExpression = criteriaBuilder.avg(coalesce);
                if (comparisonString.equals("<=")) {
                    havingConditionPredicate = criteriaBuilder.lessThanOrEqualTo(ratingExpression, (double) count);
                } else if (comparisonString.equals(">=")) {
                    havingConditionPredicate = criteriaBuilder.greaterThanOrEqualTo(ratingExpression, (double) count);
                } else if (comparisonString.equals("=")) {
                    havingConditionPredicate = criteriaBuilder.equal(ratingExpression, (double) count);
                } else {
                    errorJoiner.add("Illegal argument of method ratingValueCountComparison in ArticleSpecification.class." +
                            " Call programmer for help.");
                }
            } else {
                errorJoiner.add("Illegal argument of method ratingValueCountComparison in ArticleSpecification.class." +
                        " Call programmer for help.");
            }

            //Вытягиваем существующие условия having.
            //Если просто добавить новые условия, то существующие затрутся.
            Predicate existingRestrictions = criteriaQuery.getGroupRestriction();
            List<Predicate> predicates = new ArrayList<>();
            if (existingRestrictions != null) {
                predicates.add(existingRestrictions);
            }
            predicates.add(havingConditionPredicate);

            criteriaQuery.having(predicates.toArray(new Predicate[]{}));

            addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);

            return criteriaQuery.getRestriction();
        };
    }

//    public static Specification<Article> ratingValueCountComparison_new_NOT_FINISHED(String countString,
//                                                                    StringJoiner errorJoiner,
//                                                                    List<SingularAttribute> orderAttributesList,
//                                                                    String comparisonString,
//                                                                    String averageOrCount) {
//        //проверка и преобразование строки countLikes в Long (не int, так как вычисляем сумму по таблице, где Long id)
//        StringJoiner countError = new StringJoiner("");
//        Long count = convertStringToLong(countString, countError);
//        if (count == null) {
//            errorJoiner.add("Invalid value of count values for rating: '" + countError.toString() + "' couldn't convert to Long");
//            return Specification.where(null);
//        }
//
//        return (Specification<Article>) (root, criteriaQuery, criteriaBuilder) -> {
//            Boolean needToUseSubquery = false;
//            Join<Article, ArticleRating> ratingJoin;
//
//            //Поищем, нет ли уже в запросе join с таблицей ArticleRating.
//            //Если есть, то второй join добавлять нельзя, так как выдается неверный результат
//            List ratingJoinList = root.getJoins()
//                    .stream()
//                    .filter(articleJoin -> articleJoin.getJavaType() == ArticleRating.class)
//                    .collect(Collectors.toList());
//
//            if (ratingJoinList.size() == 0) {
//                needToUseSubquery = !root.getJoins().isEmpty();
//                //если join не обнаружен, создадим его
//                ratingJoin = root.join("ratings", JoinType.LEFT);
//                criteriaQuery
//                        .distinct(true)
//                        .groupBy(
//                                ratingJoin.get("article"),
//                                root.get(Article_.id));
//            } else {
//                needToUseSubquery = root.getJoins().size() > 1;
//                //если join обнаружим, вытягиваем готовый
//                ratingJoin = (Join<Article, ArticleRating>) ratingJoinList.get(0);
//            }
//
//            //Формируем новое условие having в зависимости от переданного параметра >=,<=,=
//            //и значения averageOrCount - принимает значения "average"  и "count",
//            //для подсчета средней оценки или количества оценок соответственно.
//            Predicate havingConditionPredicate = null;
//            Expression<? extends Long> ratingCountExpression = null;
//            Expression<? extends Double> ratingExpression = null;
//            if (averageOrCount.equals("count")) {
//                ratingCountExpression = criteriaBuilder.sum(
//                        criteriaBuilder.<Long>selectCase()
//                                .when(criteriaBuilder.isNull(ratingJoin.get("article")), 0L)
//                                .otherwise(1L)
//                );
//                if (comparisonString.equals("<=")) {
//                    havingConditionPredicate = criteriaBuilder.lessThanOrEqualTo(ratingCountExpression, count);
//                } else if (comparisonString.equals(">=")) {
//                    havingConditionPredicate = criteriaBuilder.greaterThanOrEqualTo(ratingCountExpression, count);
//                } else if (comparisonString.equals("=")) {
//                    havingConditionPredicate = criteriaBuilder.equal(ratingCountExpression, count);
//                } else {
//                    errorJoiner.add("Illegal argument of method ratingValueCountComparison in ArticleSpecification.class." +
//                            " Call programmer for help.");
//                }
//            } else if (averageOrCount.equals("average")) {
//                Coalesce<Integer> coalesce = criteriaBuilder.coalesce();
//                coalesce.value(ratingJoin.get("value"));
//                coalesce.value(0);
//                ratingExpression = criteriaBuilder.avg(coalesce);
//                if (comparisonString.equals("<=")) {
//                    havingConditionPredicate = criteriaBuilder.lessThanOrEqualTo(ratingExpression, (double) count);
//                } else if (comparisonString.equals(">=")) {
//                    havingConditionPredicate = criteriaBuilder.greaterThanOrEqualTo(ratingExpression, (double) count);
//                } else if (comparisonString.equals("=")) {
//                    havingConditionPredicate = criteriaBuilder.equal(ratingExpression, (double) count);
//                } else {
//                    errorJoiner.add("Illegal argument of method ratingValueCountComparison in ArticleSpecification.class." +
//                            " Call programmer for help.");
//                }
//            } else {
//                errorJoiner.add("Illegal argument of method ratingValueCountComparison in ArticleSpecification.class." +
//                        " Call programmer for help.");
//            }
//
//            //Вытягиваем существующие условия having.
//            //Если просто добавить новые условия, то существующие затрутся.
//            Predicate existingRestrictions = criteriaQuery.getGroupRestriction();
//            List<Predicate> predicates = new ArrayList<>();
//            if (existingRestrictions != null) {
//                predicates.add(existingRestrictions);
//            }
//            predicates.add(havingConditionPredicate);
//            if (!needToUseSubquery) {
//                criteriaQuery.having(predicates.toArray(new Predicate[]{}));
//
//                addOrderBy(orderAttributesList, root, criteriaQuery, criteriaBuilder);
//            } else {
////                // count books written by an author
////                Subquery sub = criteriaQuery.subquery(Long.class);
////                Root subRoot = sub.from(Book.class);
////                SetJoin<Book, Author> subAuthors = subRoot.join(Book_.authors);
////                sub.select(cb.count(subRoot.get(Book_.id)));
////                sub.where(cb.equal(root.get(Author_.id), subAuthors.get(Author_.id)));
////
////// check the result of the subquery
////                cq.where(cb.greaterThanOrEqualTo(sub, 3L));
//            }
//            return criteriaQuery.getRestriction();
//        };
//    }

}
