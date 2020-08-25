package ru.travelmatch.base.entities;

import javax.persistence.Column;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Farida Gareeva
 * Created 19.07.2020
 * v1.0
 */
@StaticMetamodel(Article.class)
public class Article_ {
    public static volatile SingularAttribute<Article, Long> id;
    public static volatile SingularAttribute<Article, String> title;
    public static volatile SingularAttribute<Article, String> text;
    public static volatile SingularAttribute<Article, ArticleCategory> category;
    public static volatile SingularAttribute<Article, User> author;
    public static volatile SingularAttribute<Article, LocalDateTime> created;
    public static volatile SingularAttribute<Article, LocalDateTime> lastUpdated;
    public static volatile SingularAttribute<Article, City> city;
    public static volatile SingularAttribute<Article, Language> language;
    public static volatile SingularAttribute<Article, Long> countLikes;
    public static volatile SingularAttribute<Article, Long> countDislikes;
    public static volatile SingularAttribute<Article, Long> countComments;
    public static volatile SingularAttribute<Article, Double> rating;
    public static volatile ListAttribute<Article, List<Comment>> comments;
    public static volatile ListAttribute<Article, List<ArticleLikeRating>> likes;
    public static volatile ListAttribute<Article, List<Tag>> tags;

    public static Map<String, SingularAttribute> getAttributeMap(){

        Map<String, SingularAttribute> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("text", text);
        map.put("category", category);
        map.put("author", author);
        map.put("created", created);
        map.put("lastUpdated", lastUpdated);
        map.put("city", city);
        map.put("language", language);

        return map;
    }
}

