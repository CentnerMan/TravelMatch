/**
 * @author Ostrovskiy Dmitriy
 * @created 11.07.2020
 * Awards
 * @version v1.0
 */

package ru.travelmatch.base.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users_awards")
@IdClass(AwardsUser.UserAwardsId.class)
public class AwardsUser {

    //Пользователь
    @Id
    @Column(name = "user_id")
    private Long userId;

    //Список достижений пользоваеля
    @Id
    @Column(name = "award_id")
    private Long awardId;

    //Дата получения достижения
    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;

    //    @JsonManagedReference
//    @ManyToOne(optional = false)
//    private User user;
//
//    @JsonBackReference
//    @ManyToMany (mappedBy = "award",
//            cascade = CascadeType.ALL)
//    private List<Award> awards;

    @EqualsAndHashCode
    @Access(value = AccessType.FIELD)
    @Setter
    public static class UserAwardsId implements Serializable {
        private Long userId;
        private Long awardId;
    }

}
