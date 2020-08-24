package ru.travelmatch.base.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "simple_adverts")
public class SimpleAdvert {
    public enum Type {
        PRODUCT, SERVICE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
}
