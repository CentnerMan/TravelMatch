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
 * Сущность для хранения метчей. (Раздел для общения пользователей, которые хотят познакомиться
 * с местными жителями в стране путешествия или являются местными жителями и хотят познакомиться
 * с путешественниками, рассказать им про культуру, попрактиковать иностранный язык)
 * targetStatus - признак для деления метчей на принадлежащие путешественникам и местным жителям.
 * isActual - деление метчей на актуальные и нет.
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "match_profiles")
public class MatchProfile {

    public enum TargetStatus{LOCAL,TRAVELER}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text", length = 10000)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_status", nullable = false)
    private TargetStatus targetStatus;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    private Currency currency;

    @ManyToOne (optional = false)
    private City city;

    @JsonManagedReference
    @ManyToOne(optional = false)
    private User user;

    @ManyToOne (optional = false)
    private Language language;

    @Column(name = "is_actual", nullable = false)
    private Boolean isActual;

    @Column(name = "begin_date")
    private LocalDate beginDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany
    @JoinTable(name = "match_profiles_tags",
            joinColumns = @JoinColumn(name = "match_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "matchProfile")
    private List<MatchRequest> myMatchRequests;
}