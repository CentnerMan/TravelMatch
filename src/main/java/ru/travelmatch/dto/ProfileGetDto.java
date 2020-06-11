/**
 * @author Ostrovskiy Dmitriy
 * @created 11.06.2020
 * ProfileDto
 * @version v1.0
 */

package ru.travelmatch.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.travelmatch.base.entities.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProfileGetDto {

    @NotNull(message = "Заполните все поля")
    @Size(min = 4, max = 100, message = "Имя пользователя от 4 до 100 символов")
    private final String username;

    @NotNull(message = "Заполните все поля")
    @Size(min = 4, max = 100, message = "Пароль от 4 до 100 символов")
    private final String password;

    @NotNull(message = "Должно быть имя")
    private String firstName;

    //    @NotNull(message = "Заполните все поля")
    private String lastName;

    @NotNull(message = "Заполните все поля")
    @Email(message = "Должно быть xxx@xxx.xxx")
    private String email;

    //    @NotNull(message = "Заполните все поля")
    private LocalDate birthday;

    //    @NotNull(message = "Заполните все поля")
    private User.Sex sex;

    //    @NotNull(message = "Заполните все поля")
    private String about;

    @NotNull(message = "Заполните все поля")
    private User.StatusActivity statusActivity;

    private LocalDateTime created;

    private LocalDateTime lastUpdated;

    private List<Article> favoriteArticles;

    private List<Advert> favoriteAdverts;

    private List<MatchProfile> favoriteMatchProfiles;

    private List<Advert> myAdverts;

    private List<MatchProfile> myMatchProfiles;

    private List<MatchRequest> myMatchRequests;

    private List<Article> myArticles;

    private List<Tag> tags;

}