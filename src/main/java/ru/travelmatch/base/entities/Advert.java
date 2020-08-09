package ru.travelmatch.base.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * Created 08/06/2020
 * v1.0
 * Сущность для хранения объявлений купли-продажи товаров и услуг.
 * AdvertType - деление объявлений на куплю/продам
 * ProductType - деление объявлений на товары и услуги
 * ProductCondition - деление товаров на новые и б/у
 * beginDate, endDate - период актуальности объявления
 * isActual - деление объявлений на актуальные и нет.
 * Актуальность может менять владелец объявления, может сбрасывать админ в случае жалоб (см.AdvertClaim),
 * флаг может сбрасываться по истечение периода актуальности
 */

@Entity
//@Data //"java.lang.StackOverflowError" with this annotation - changed to getter setter
@Getter
@Setter
@NoArgsConstructor
@Table(name = "adverts")
public class Advert {

    public enum AdvertType {SALE, BUY}
    public enum ProductType {PRODUCT, SERVICE}
    public enum ProductCondition {NEW, USED}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text", length = 10000)
    private String text;

    @Column (name = "category_id")
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable=false, updatable=false)
    private AdvertCategory category;

    @Column (name = "currency_id")
    private Long currencyId;

    @ManyToOne
    @JoinColumn(name = "currency_id", insertable=false, updatable=false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AdvertType type;

    @Column (name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_condition")
    private ProductCondition productCondition;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "price")
    private Long price;

/*
    @JsonManagedReference
    @ManyToOne
    private City city;
 */
    // @JsonManagedReference
    //  @ManyToOne(optional = false)
    //  private User user;
/*
    @JsonManagedReference
    @ManyToOne (optional = false)
    private Language language;

 */

    @Column(name = "is_actual", nullable = false)
    private Boolean isActual;

    @Column(name = "begin_date")
    private LocalDate beginDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    //    //список ссылок на фотографии товара
// не получилось, hibernate ругается:
//    org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaConfiguration.class]: Invocation of init method failed; nested exception is org.hibernate.AnnotationException: Use of @OneToMany or @ManyToMany targeting an unmapped class: ru.travelmatch.base.entities.User.pictURLs[java.lang.String]
//    @OneToMany
//    @JoinTable(name = "adverts_pictURLs",
//            joinColumns = @JoinColumn(name = "advert_id"),
//            inverseJoinColumns = @JoinColumn(name = "pictURL"))
//    private List<String> pictURLs;


    //   @JsonBackReference
    //  @OneToMany(mappedBy = "advert", orphanRemoval = true)
    //  private List<AdvertClaim> claims;

    // @ManyToMany
    //  @JoinTable(name = "adverts_tags",
    //        joinColumns = @JoinColumn(name = "advert_id"),
    //           inverseJoinColumns = @JoinColumn(name = "tag_id"))
    //   private List<Tag> tags;

}