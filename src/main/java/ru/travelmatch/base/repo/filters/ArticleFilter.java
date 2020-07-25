package ru.travelmatch.base.repo.filters;

import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.Article;
import ru.travelmatch.base.entities.Article_;
import ru.travelmatch.base.repo.specifications.ArticleSpecification;

import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author Farida Gareeva
 * Created 11.07.2020
 * v1.0
 */
public class ArticleFilter {

    private Specification<Article> specification;

    public List<SingularAttribute> orderList;

    public Specification<Article> getSpecification() {
        return specification;
    }

    public List<SingularAttribute> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<SingularAttribute> orderList) {
        this.orderList = orderList;
    }

    public ArticleFilter(HttpServletRequest request, StringJoiner errorJoiner) {
        specification = Specification.where(null);
        orderList = new ArrayList<>();
        orderList.add(Article_.created);

        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            specification = specification.and(ArticleSpecification.idEq(request.getParameter("id"), errorJoiner));
        }

        if (request.getParameter("author_id") != null && !request.getParameter("author_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.authorEq(request.getParameter("author_id"), errorJoiner, orderList));
        }

        if (request.getParameter("category_id") != null && !request.getParameter("category_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.categoryEq(request.getParameter("category_id"), errorJoiner, orderList));
        }

        if (request.getParameter("city_id") != null && !request.getParameter("city_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.cityEq(request.getParameter("city_id"), errorJoiner, orderList));
        }

        if (request.getParameter("language_id") != null && !request.getParameter("language_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.languageEq(request.getParameter("language_id"), errorJoiner, orderList));
        }

        if (request.getParameter("text") != null && !request.getParameter("text").isEmpty()) {
            specification = specification.and(ArticleSpecification.textContains(request.getParameter("text"), errorJoiner, orderList));
        }

        if (request.getParameter("title_equal") != null && !request.getParameter("title_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.titleEq(request.getParameter("title_equal"), errorJoiner, orderList));
        }

        if (request.getParameter("title_contains") != null && !request.getParameter("title_contains").isEmpty()) {
            specification = specification.and(ArticleSpecification.titleContains(request.getParameter("title_contains"), errorJoiner, orderList));
        }

        if (request.getParameter("created_equal") != null && !request.getParameter("created_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateEq(request.getParameter("created_equal"), errorJoiner, "created", orderList));
        }

        if (request.getParameter("created_before") != null && !request.getParameter("created_before").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateLessThan(request.getParameter("created_before"), errorJoiner, "created", orderList));
        }

        if (request.getParameter("created_after") != null && !request.getParameter("created_after").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateGreaterThan(request.getParameter("created_after"), errorJoiner, "created", orderList));
        }

        if (request.getParameter("updated_equal") != null && !request.getParameter("updated_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateEq(request.getParameter("updated_equal"), errorJoiner, "lastUpdated", orderList));
        }

        if (request.getParameter("updated_before") != null && !request.getParameter("updated_before").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateLessThan(request.getParameter("updated_before"), errorJoiner, "lastUpdated", orderList));
        }

        if (request.getParameter("updated_after") != null && !request.getParameter("updated_after").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateGreaterThan(request.getParameter("updated_after"), errorJoiner, "lastUpdated", orderList));
        }

        if (request.getParameter("all_tag_id") != null && !request.getParameter("all_tag_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.allTagIdIn(request.getParameter("all_tag_id"), errorJoiner, orderList));
        }

        if (request.getParameter("likes_equal") != null && !request.getParameter("likes_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesComparison(request.getParameter("likes_equal"), errorJoiner, orderList, 1, "="));
        }

        if (request.getParameter("dislikes_equal") != null && !request.getParameter("dislikes_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesComparison(request.getParameter("dislikes_equal"), errorJoiner, orderList, -1, "="));
        }

        if (request.getParameter("likes_greaterOrEqual") != null && !request.getParameter("likes_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesComparison(request.getParameter("likes_greaterOrEqual"), errorJoiner, orderList, 1, ">="));
        }

        if (request.getParameter("likes_lessOrEqual") != null && !request.getParameter("likes_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesComparison(request.getParameter("likes_lessOrEqual"), errorJoiner, orderList, 1, "<="));
        }

        if (request.getParameter("dislikes_greaterOrEqual") != null && !request.getParameter("dislikes_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesComparison(request.getParameter("dislikes_greaterOrEqual"), errorJoiner, orderList, -1, ">="));
        }

        if (request.getParameter("dislikes_lessOrEqual") != null && !request.getParameter("dislikes_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesComparison(request.getParameter("dislikes_lessOrEqual"), errorJoiner, orderList, -1, "<="));
        }

        if (request.getParameter("rating_value_count_greaterOrEqual") != null && !request.getParameter("rating_value_count_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.ratingValueCountComparison(request.getParameter("rating_value_count_greaterOrEqual"), errorJoiner, orderList, ">=","count"));
        }

        if (request.getParameter("rating_value_count_lessOrEqual") != null && !request.getParameter("rating_value_count_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.ratingValueCountComparison(request.getParameter("rating_value_count_lessOrEqual"), errorJoiner, orderList, "<=","count"));
        }

        if (request.getParameter("rating_value_count_equal") != null && !request.getParameter("rating_value_count_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.ratingValueCountComparison(request.getParameter("rating_value_count_equal"), errorJoiner, orderList, "=","count"));
        }

        if (request.getParameter("rating_greaterOrEqual") != null && !request.getParameter("rating_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.ratingValueCountComparison(request.getParameter("rating_greaterOrEqual"), errorJoiner, orderList, ">=","average"));
        }

        if (request.getParameter("rating_lessOrEqual") != null && !request.getParameter("rating_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.ratingValueCountComparison(request.getParameter("rating_lessOrEqual"), errorJoiner, orderList, "<=","average"));
        }

        if (request.getParameter("rating_equal") != null && !request.getParameter("rating_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.ratingValueCountComparison(request.getParameter("rating_equal"), errorJoiner, orderList, "=","average"));
        }
        //TODO добавить варианты отбора в списках


    }
}
