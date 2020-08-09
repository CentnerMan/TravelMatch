package ru.travelmatch.base.entities;

import org.hibernate.metamodel.model.domain.spi.BagPersistentAttribute;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 03.07.2020
 * v1.0
 */
@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> phoneNumber;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, LocalDate> birthday;
    public static volatile SingularAttribute<User, User.Sex> sex;
    public static volatile SingularAttribute<User, String> about;
    public static volatile SingularAttribute<User, User.StatusActivity> statusActivity;
    public static volatile SingularAttribute<User, LocalDateTime> created;
    public static volatile SingularAttribute<User, LocalDateTime> lastUpdated;
    public static volatile ListAttribute<User, List<Article>> favoriteArticles;
    public static volatile ListAttribute<User, List<Advert>> favoriteAdverts;
    public static volatile ListAttribute<User, List<MatchProfile>> favoriteMatchProfiles;
    public static volatile ListAttribute<User, List<Advert>> myAdverts;
    public static volatile ListAttribute<User, List<MatchProfile>> myMatchProfiles;
    public static volatile ListAttribute<User, List<MatchRequest>> myMatchRequests;
    public static volatile ListAttribute<User, List<Article>> myArticles;
    public static volatile ListAttribute<User, List<Tag>> tags;
    public static volatile ListAttribute<User, List<LanguageSkill>> languages;
    public static volatile BagPersistentAttribute<User, Collection<Role>> roles;
    public static volatile ListAttribute<User, List<Award>> awards;
    public static volatile ListAttribute<User, List<Article>> articles;
}
