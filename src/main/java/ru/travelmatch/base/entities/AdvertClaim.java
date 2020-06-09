package ru.travelmatch.base.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 08/06/2020
 * v1.0
 * Сущность для хранения жалоб пользователей на объявления купли-продажи (см.Advert).
 * reasonClaim - одна из наиболее популярных причин жалоб (см.ReasonClaim).
 * text - дополнительно может быть заполнен текст жалобы
 * status - статус жалобы, New - только созданная, CLOSED - рассмотренная админом.
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "advert_claims")
public class AdvertClaim {

    public enum Status{NEW,CLOSED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonManagedReference
    @ManyToOne(optional = false)
    private Advert advert;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne(optional = false)
    private ReasonClaim reasonClaim;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @JsonManagedReference
    @ManyToOne(optional = false)
    private User user;

}