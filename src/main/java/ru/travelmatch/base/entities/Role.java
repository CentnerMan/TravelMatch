package ru.travelmatch.base.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {
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

  public Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
