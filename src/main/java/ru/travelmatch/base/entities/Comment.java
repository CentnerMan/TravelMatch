package ru.travelmatch.base.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 08/06/2020
 * v1.0
 * Сущность для хранения комментариев авторизованных пользователей к статьям
 * parent_id - ссылка на родительский комментарий на случай возможности создания дерева комментариев
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;


  @Column(name = "text", length = 10000)
  private String text;

  @JsonManagedReference
  @ManyToOne(optional = false)
  private User user;

  @JsonManagedReference
  @ManyToOne(optional = false)
  private Article article;

  @Column(name = "parent_id")
  private Long parentId;

  @CreationTimestamp
  @Column(name = "created")
  private LocalDateTime created;

  @UpdateTimestamp
  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;

}
