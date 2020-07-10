package ru.travelmatch.base.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    public enum Sex {MALE, FEMALE}

    public enum StatusActivity {ACTIVE, ARCHIVED, BANNED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone_number", unique = true, nullable = false)
    private String
            phoneNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "about")
    private String about;

    @Column(name = "status_activity")
    @Enumerated(EnumType.STRING)
    private StatusActivity statusActivity;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "favorite_articles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> favoriteArticles;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "favorite_adverts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "advert_id"))
    private List<Advert> favoriteAdverts;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "favorite_match_profiles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "match_profile_id"))
    private List<MatchProfile> favoriteMatchProfiles;

    @JsonBackReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Advert> myAdverts;

    @JsonBackReference
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<MatchProfile> myMatchProfiles;

    @JsonBackReference
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private List<MatchRequest> myMatchRequests;

    @JsonBackReference
    @OneToMany(mappedBy = "author")
    private List<Article> myArticles;

    @ManyToMany
    @JoinTable(name = "users_tags",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

//    список ссылок на фотографии пользователя
//    не получилось, hibernate ругается:
//    org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory'
//    defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]:
//    Invocation of init method failed; nested exception is org.hibernate.AnnotationException:
//    Use of @OneToMany or @ManyToMany targeting an unmapped class: ru.travelmatch.base.entities.User.pictURLs[java.lang.String]

//    @OneToMany
//    @JoinTable(name = "users_pictURLs",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "pictURL"))
//    private List<String> pictURLs;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LanguageSkill> languages;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

}
