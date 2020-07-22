package ru.travelmatch.base.entities;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @Author Farida Gareeva
 * Created 03.07.2020
 * v1.0
 */
@StaticMetamodel(User.class)
public class User_ {
    public static volatile SingularAttribute<User, Long> id;
}
