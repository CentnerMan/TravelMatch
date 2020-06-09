package ru.travelmatch.base.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 08/06/2020
 * v1.0
 * Класс - справочник жалоб на объявление (см.Advert)(н-р, не отвечает телефон, уже продано и т.д.)
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "reason_claims")
public class ReasonClaim {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @CreationTimestamp
  @Column(name = "created")
  private LocalDateTime created;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;
}
