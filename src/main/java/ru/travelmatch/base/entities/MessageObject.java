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

/**
 * @Author Farida Gareeva
 * Created 09/06/2020
 * v1.0
 * Сущность для хранения основания начала переписки в чате.
 * Из двух полей Match_request и Advert должно быть заполнено только одно - это и есть причина переписки.
 * isActual - признак актуальности переписки. Если false, переписка завершена.
 * finishDate - дата завершения переписки
 * finishUser - User,инициатор завершения переписки
 * finishReason - String, описание причины завершения переписки.
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "message_objects")
public class MessageObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private MatchProfile matchProfile;

    @ManyToOne
    private Advert advert;

    @Column(name = "is_actual", nullable = false)
    private Boolean isActual;

    @Column(name = "finish_reason")
    private String finishReason;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @ManyToOne
    private User finishUser;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
