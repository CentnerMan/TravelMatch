package ru.travelmatch.base.entities;//package ru.geek.news_portal.newsportal.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 08/06/2020
 * v1.0
 * В этой сущности хранятся данные о том, насколько хорошо пользователь владеет языками.
 * Степень владения языком оценивается по 5-ти балльной шкале.
 * Эти данные помогут предложить пользователю только те статьи и объявления, которые
 * написаны на доступных ему языках. А также пользователь сможет подобрать Activities
 * с подходящими языковыми свойствами.
 * Ключ уникальности - (User,Language)
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "language_skills")
@IdClass(LanguageSkill.UserLanguageId.class)
public class LanguageSkill {

    @Id
    @Column(name = "language_id", nullable = false)
    private Long language;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long user;

    @Column(name = "value",nullable = false)
    private int value;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @EqualsAndHashCode
    @Access(value = AccessType.FIELD)
    @Setter
    public static class UserLanguageId implements Serializable {
        private Long language;
        private Long user;
    }
}
