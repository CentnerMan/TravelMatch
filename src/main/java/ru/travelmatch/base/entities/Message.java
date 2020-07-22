package ru.travelmatch.base.entities;

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
 * Сущность для хранения сообщений.
 * reason - MessageObject, причина, основание данной переписки.
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    private User sender;

    @ManyToOne(optional = false)
    private User receiver;

    @Column(name = "text",length = 10_000,nullable = false)
    private String text;

    @ManyToOne(optional = false)
    private MessageObject reason;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
