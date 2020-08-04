/**
 * @author Ostrovskiy Dmitriy
 * @created 11.06.2020
 * ProfileFreeDto
 * Класс DTO возвращает открытые данные пользователя и статистику
 * @version v1.0
 */

package ru.travelmatch.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.travelmatch.base.entities.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProfileFreeGetDto {

    private Long id;

    private final String username;

    private String firstName;

    private String lastName;

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

    public ProfileFreeGetDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.sex = user.getSex();
        this.about = user.getAbout();
        this.statusActivity = user.getStatusActivity();
        this.created = user.getCreated();
        this.lastUpdated = user.getLastUpdated();

        this.favoriteArticles = user.getFavoriteArticles();
        this.favoriteAdverts = user.getFavoriteAdverts();
        this.favoriteMatchProfiles = user.getFavoriteMatchProfiles();
        this.myAdverts = user.getMyAdverts();
        this.myMatchProfiles = user.getMyMatchProfiles();
        this.myMatchRequests = user.getMyMatchRequests();
        this.myArticles = user.getMyArticles();
        this.articles = user.getArticles();
        this.tags = user.getTags();
    }
}
