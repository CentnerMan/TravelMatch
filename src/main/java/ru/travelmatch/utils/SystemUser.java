
package ru.travelmatch.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Anatoly Lebedev
 * @fix 11.07.2020 Ostrovskiy Dmitriy
 * @version 1.0.1 11.06.2020
 * @link https://github.com/Centnerman
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.utils.validation.FieldMatch;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
//@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemUser {

    //For create user must be null, for update must be Not NULL
    private Long id;

    @NotNull(message = "Field must not be empty!")
    @Size(min = 4, max = 100, message = "Too short <4!")
    private String phoneNumber;

    @NotNull(message = "Field must not be empty!")
    @Size(min = 4, max = 100, message = "Too short <4!")
    private String username;

    @NotNull(message = "Field must not be empty!")
    @Size(min = 4, max = 100, message = "Too short <4!")
    //    @Pattern(regexp = "(?=^.{4,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z])", message = "Must be a-Z,0-9,_'^&/+! and <4")
    private String password;

    @NotNull(message = "Not Null!")
    @Size(min = 4, max = 30, message = "Too short <4!")
    //    @Pattern(regexp = "(?=^.{4,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z])", message = "Must be a-Z,0-9,_'^&/+! and <4")
    private String matchingPassword;

    @NotNull(message = "Field must not be empty!")
    private String firstName;

    //    @NotNull(message = "Field must not be empty!")
    private String lastName;

    @NotNull(message = "Field must not be empty!")
    @Email(message = "Must be xxx@xxx.xxx")
    private String email;

    @NotNull(message = "Field must not be empty!")
    private LocalDate birthday;

    //    @NotNull(message = "Field must not be empty!")
    private User.Sex sex;

    //    @NotNull(message = "Field must not be empty!")
    private String about;

    private User.StatusActivity statusActivity;

    private LocalDateTime created;

    private LocalDateTime lastUpdated;

    @AssertTrue(message="The password fields must match!")
    private boolean isValid() {
        return this.password.equals(this.matchingPassword);
    }

//    public SystemUser(String username, String password, String firstName, String lastName,
//                      String email, LocalDate birthday, User.Sex sex, String about,
//                      User.StatusActivity statusActivity, LocalDateTime created, LocalDateTime lastUpdated) {
//        this.username = username;
//        this.password = password;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.birthday = birthday;
//        this.sex = sex;
//        this.about = about;
//        this.statusActivity = statusActivity;
//        this.created = created;
//        this.lastUpdated = lastUpdated;
//    }

}
