/**
 * @author Ostrovskiy Dmitriy
 * @created 11.06.2020
 * ProfilePersonalDto
 * Класс DTO возвращает пользователю его личные данные и статистику
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.travelmatch.base.entities.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProfilePersonalDto {

    //For create user must be null, for update must be Not NULL
    private Long id;

    private String phoneNumber;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate birthday;

    private User.Sex sex;

    private String about;

    private User.StatusActivity statusActivity;

    private LocalDateTime created;

    private LocalDateTime lastUpdated;

// Statistical data user (Статистические данные пользователя)
    private List<Article> favoriteArticles;
    private List<Advert> favoriteAdverts;
    private List<MatchProfile> favoriteMatchProfiles;
    private List<Advert> myAdverts;
    private List<MatchProfile> myMatchProfiles;
    private List<MatchRequest> myMatchRequests;
    private List<Article> myArticles;
    private List<Article> articles;
    private List<Tag> tags;
    private List<Award> awards;

    public ProfilePersonalDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.sex = user.getSex();
        this.about = user.getAbout();
        this.statusActivity = user.getStatusActivity();
        this.created = user.getCreated();
        this.lastUpdated = user.getLastUpdated();
        this.phoneNumber = user.getPhoneNumber();

        // Statistical data user
        this.favoriteArticles = user.getFavoriteArticles();
        this.favoriteAdverts = user.getFavoriteAdverts();
        this.favoriteMatchProfiles = user.getFavoriteMatchProfiles();
        this.myAdverts = user.getMyAdverts();
        this.myMatchProfiles = user.getMyMatchProfiles();
        this.myMatchRequests = user.getMyMatchRequests();
        this.myArticles = user.getMyArticles();
        this.articles = user.getArticles();
        this.tags = user.getTags();
        this.awards = user.getAwards();
    }
}
