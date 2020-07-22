package ru.travelmatch.base.entities;

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
 * Created 09/06/2020
 * v1.0
 * Сущность для хранения откликов пользователей на метчи (см. MatchProfile).
 * Пользователь отправляет запрос (MatchRequest) на понравившийся метч.
 * Владелец метча может принять этот запрос (Status = ACCEPTED),
 * либо отклонить его (Status = REFUSED). В случае положительного отклика
 * владельца между покупателем и владельцем открывается возможность обмена сообщениями.
 * owner - User, владелец метча, на который делается данный запрос.
 * customer - User, пользователь, заинтересовавшийся метчем и отправивший свой запрос.
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "match_requests")
public class MatchRequest {

    public enum StatusRequest{NEW,ACCEPTED,REFUSED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonManagedReference
    @ManyToOne(optional = false)
    private MatchProfile matchProfile;

    @JsonManagedReference
    @ManyToOne(optional = false)
    private User owner;

    @JsonManagedReference
    @ManyToOne(optional = false)
    private User customer;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusRequest status;

}