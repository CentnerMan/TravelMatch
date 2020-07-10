package ru.travelmatch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.travelmatch.base.entities.User;

import java.time.LocalDate;
import java.time.Period;

/**
 * @Author Farida Gareeva
 * Created 01/07/2020
 * v1.0
 * ДТО сущности User для целей фильтрации
 */

@Getter
@Setter
@NoArgsConstructor
public class UserSimpleDTO {
    private Long id;
    private LocalDate birthday;
    private String sex;
    private String about;
    private int age;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String statusActivity;

    public UserSimpleDTO(User user) {
        this.id = user.getId();
        this.birthday = user.getBirthday();
        this.sex = user.getSex()!=null?user.getSex().toString():"";
        this.about = user.getAbout();
        this.age = ageCount(user.getBirthday());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.username = user.getUsername();
        this.statusActivity = user.getStatusActivity()!=null?user.getStatusActivity().toString():"";
    }

    private int ageCount(LocalDate birthday) {
        Period period = Period.between(birthday, LocalDate.now());
        return period.getYears();
    }
}
