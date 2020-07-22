package ru.travelmatch.base.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 08/06/2020
 * v1.0
 * Сущность для хранения статей
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "text", length = 10000)
    private String text;

    @JsonManagedReference
    @ManyToOne (optional = false)
    private ArticleCategory category;

    @JsonManagedReference
    @ManyToOne
    private User author;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @ManyToOne
    private City city;

    @ManyToOne
    private Language language;

    @JsonBackReference
    @OneToMany(mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments;

    @OneToMany(mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ArticleLike> likes;

    @ManyToMany
    @JoinTable(name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ArticleRating> ratings;

}