package ru.travelmatch.base.repo.filters;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.Article;
import ru.travelmatch.base.repo.specifications.ArticleSpecification;
import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

/**
 * Фильтр по статьям.
 * Производит поиск параметров, полученных от пользователя в request, и
 * перенаправляет строковые значения параметров в соответствующие методы
 * класса {@link ArticleSpecification} для формирования спецификаций.
 *
 * @Author Farida Gareeva
 * Created 11.07.2020
 * v1.0
 */
public class ArticleFilter {

    public static final int PAGE_SIZE_DEFAULT = getPageSizeDefault();

    private static int getPageSizeDefault() {
        SpringDataWebProperties properties = new SpringDataWebProperties();
        return properties.getPageable().getDefaultPageSize();
    }

    public enum ComparisonOperations {EQUAL, GREATER, LESS}

    public enum ArticleValue {

        LIKE("like", "likeDislike"),
        DISLIKE("dislike", "likeDislike"),
        RATING_COUNT("count of values for article rating", "rating"),
        RATING_AVG("average rating of article", "rating");

        private String title;
        private String path;

        private ArticleValue(String title, String path) {
            this.title = title;
            this.path = path;
        }

        public String getTitle() {
            return title;
        }

        public String getPath() {
            return path;
        }
    }

    private Specification<Article> specification;
    private int pageNumber = 1;
    private int pageSize;
    private Sort.Direction directionSort;

    public Specification<Article> getSpecification() {
        return specification;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param request     - запрос от клиента
     * @param errorJoiner - StringJoiner для сбора всех ошибок в строке запроса в рамках одного запроса
     */
    public ArticleFilter(HttpServletRequest request, StringJoiner errorJoiner) {

        specification = Specification.where(null);
        pageSize = PAGE_SIZE_DEFAULT;
        directionSort = Sort.Direction.ASC;

        if (request.getParameter("page_number") != null && !request.getParameter("page_number").isEmpty()) {
            pageNumber = convertFromStringAndCheckPageSizeAndNumber(request.getParameter("page_number"), "number of page", errorJoiner);
        }

        if (request.getParameter("page_size") != null && !request.getParameter("page_size").isEmpty()) {
            pageSize = convertFromStringAndCheckPageSizeAndNumber(request.getParameter("page_size"), "total count elements on a page", errorJoiner);
        }

        if (request.getParameter("order_direction") != null && !request.getParameter("order_direction").isEmpty()) {
            getAndCheckDirectionSort(request.getParameter("order_direction"), "direction for ordering", errorJoiner);
        }

        if (request.getParameter("order_by_properties") != null && !request.getParameter("order_by_properties").isEmpty()) {
            specification = specification.and(ArticleSpecification.addOrderByProperties(request.getParameter("order_by_properties"),directionSort, errorJoiner));
        }

        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            specification = specification.and(ArticleSpecification.idEq(request.getParameter("id"), errorJoiner));
        }

        if (request.getParameter("author_id") != null && !request.getParameter("author_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.authorEq(request.getParameter("author_id"), errorJoiner));
        }

        if (request.getParameter("category_id") != null && !request.getParameter("category_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.categoryEq(request.getParameter("category_id"), errorJoiner));
        }

        if (request.getParameter("city_id") != null && !request.getParameter("city_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.cityEq(request.getParameter("city_id"), errorJoiner));
        }

        if (request.getParameter("language_id") != null && !request.getParameter("language_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.languageEq(request.getParameter("language_id"), errorJoiner));
        }

        if (request.getParameter("text") != null && !request.getParameter("text").isEmpty()) {
            specification = specification.and(ArticleSpecification.textContains(request.getParameter("text"), errorJoiner));
        }

        if (request.getParameter("title_equal") != null && !request.getParameter("title_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.titleEq(request.getParameter("title_equal"), errorJoiner));
        }

        if (request.getParameter("title_contains") != null && !request.getParameter("title_contains").isEmpty()) {
            specification = specification.and(ArticleSpecification.titleContains(request.getParameter("title_contains"), errorJoiner));
        }

        if (request.getParameter("created_equal") != null && !request.getParameter("created_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateEq(request.getParameter("created_equal"), errorJoiner, "created"));
        }

        if (request.getParameter("created_before") != null && !request.getParameter("created_before").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateLessThan(request.getParameter("created_before"), errorJoiner, "created"));
        }

        if (request.getParameter("created_after") != null && !request.getParameter("created_after").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateGreaterThan(request.getParameter("created_after"), errorJoiner, "created"));
        }

        if (request.getParameter("updated_equal") != null && !request.getParameter("updated_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateEq(request.getParameter("updated_equal"), errorJoiner, "lastUpdated"));
        }

        if (request.getParameter("updated_before") != null && !request.getParameter("updated_before").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateLessThan(request.getParameter("updated_before"), errorJoiner, "lastUpdated"));
        }

        if (request.getParameter("updated_after") != null && !request.getParameter("updated_after").isEmpty()) {
            specification = specification.and(ArticleSpecification.dateGreaterThan(request.getParameter("updated_after"), errorJoiner, "lastUpdated"));
        }

        if (request.getParameter("likes_equal") != null && !request.getParameter("likes_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("likes_equal"),
                    errorJoiner, ArticleValue.LIKE, ComparisonOperations.EQUAL));
        }

        if (request.getParameter("dislikes_equal") != null && !request.getParameter("dislikes_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("dislikes_equal"),
                    errorJoiner, ArticleValue.DISLIKE, ComparisonOperations.EQUAL));
        }

        if (request.getParameter("likes_greaterOrEqual") != null && !request.getParameter("likes_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("likes_greaterOrEqual"),
                    errorJoiner, ArticleValue.LIKE, ComparisonOperations.GREATER));
        }

        if (request.getParameter("likes_lessOrEqual") != null && !request.getParameter("likes_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("likes_lessOrEqual"),
                    errorJoiner, ArticleValue.LIKE, ComparisonOperations.LESS));
        }

        if (request.getParameter("dislikes_greaterOrEqual") != null && !request.getParameter("dislikes_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("dislikes_greaterOrEqual"),
                    errorJoiner, ArticleValue.DISLIKE, ComparisonOperations.GREATER));
        }

        if (request.getParameter("dislikes_lessOrEqual") != null && !request.getParameter("dislikes_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("dislikes_lessOrEqual"),
                    errorJoiner, ArticleValue.DISLIKE, ComparisonOperations.LESS));
        }

        if (request.getParameter("rating_value_count_greaterOrEqual") != null && !request.getParameter("rating_value_count_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("rating_value_count_greaterOrEqual"),
                    errorJoiner, ArticleValue.RATING_COUNT, ComparisonOperations.GREATER));
        }

        if (request.getParameter("rating_value_count_lessOrEqual") != null && !request.getParameter("rating_value_count_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("rating_value_count_lessOrEqual"),
                    errorJoiner, ArticleValue.RATING_COUNT, ComparisonOperations.LESS));
        }

        if (request.getParameter("rating_value_count_equal") != null && !request.getParameter("rating_value_count_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("rating_value_count_equal"),
                    errorJoiner, ArticleValue.RATING_COUNT, ComparisonOperations.EQUAL));
        }

        if (request.getParameter("rating_greaterOrEqual") != null && !request.getParameter("rating_greaterOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("rating_greaterOrEqual"),
                    errorJoiner, ArticleValue.RATING_AVG, ComparisonOperations.GREATER));
        }

        if (request.getParameter("rating_lessOrEqual") != null && !request.getParameter("rating_lessOrEqual").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("rating_lessOrEqual"),
                    errorJoiner, ArticleValue.RATING_AVG, ComparisonOperations.LESS));
        }

        if (request.getParameter("rating_equal") != null && !request.getParameter("rating_equal").isEmpty()) {
            specification = specification.and(ArticleSpecification.likesDislikesRatingsComparison(request.getParameter("rating_equal"),
                    errorJoiner, ArticleValue.RATING_AVG, ComparisonOperations.EQUAL));
        }

        //проверка параметра на теги ОБЯЗАТЕЛЬНО должна быть после проверок на лайки и рейтинги,
        //так как CriteriaQuery не позволяет делать выборку из вложенных запросов.
        //Вложенные запросы можно использовать только в части запроса where, куда и будет добавлен
        //подзапрос по тегам.
        if (request.getParameter("tags_id") != null && !request.getParameter("tags_id").isEmpty()) {
            specification = specification.and(ArticleSpecification.allTagIdIn(request.getParameter("tags_id"), errorJoiner));
        }

    }

    private void getAndCheckDirectionSort(String stringDirectionSort, String directionTitle, StringJoiner errorJoiner) {
        this.directionSort = Sort.Direction.ASC;
        try {
            this.directionSort = Sort.Direction.fromString(stringDirectionSort);
        } catch (IllegalArgumentException ex) {
            errorJoiner.add(ex.getMessage());
        }
    }

    private int convertFromStringAndCheckPageSizeAndNumber(String stringParamValue, String paramTitle, StringJoiner errorJoiner) {

        int param = 1;
        if (stringParamValue != null && !stringParamValue.isEmpty()) {
            try {
                param = Integer.parseInt(stringParamValue);
                if (param <= 0) {
                    errorJoiner.add("Invalid value of '" + paramTitle + "' = " + stringParamValue + " should be greater than 0");
                }

            } catch (NumberFormatException ex) {
                errorJoiner.add("Invalid value of '" + paramTitle + "' = " + stringParamValue + " couldn't convert to int");
            }
        }
        return param;
    }
}
