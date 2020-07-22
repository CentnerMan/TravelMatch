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
import java.util.List;

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "countries")
public class Country {
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

  @JsonManagedReference
  @OneToMany(mappedBy = "country",
          cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST}
  )
  private List<City> cities;
}
