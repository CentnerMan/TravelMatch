/**
 * @author Ostrovskiy Dmitriy
 * @created 11.07.2020
 * Awards
 * @version v1.0
 */

package ru.travelmatch.base.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "awards")
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //Название награды
    @Column(name = "title", unique = true)
    private String title;

    @ManyToMany
    @JoinTable(name = "users_awards",
            joinColumns = @JoinColumn(name = "award_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    //Значение для присвоения награды
    @Column(name = "count")
    private Integer count;

}
