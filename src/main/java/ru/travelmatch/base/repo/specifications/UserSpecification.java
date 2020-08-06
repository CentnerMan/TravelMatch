package ru.travelmatch.base.repo.specifications;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.*;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Farida Gareeva
 * Created 01/07/2020
 * v1.0
 * Спецификация ля отборов по сущности User и его свойствам
 * (Отборы производятся
 * 1. по полям сущности User
 * 2. по списку тегов с условием И, т.е. в отбор должны попадать только
 * те пользователи, у которых есть все теги, указанные в списке)
 * 3. по списку языков. Но одновременно должны быть заданы сразу два параметра:
 * список языков и степень владения языками. При этом в отбор попадают те пользователи,
 * у которых есть хотя бы один из языков, указанных в списке, и степень владения этим
 * языком не ниже, чем указано в параметре.
 */

public class UserSpecification {

    public static Specification<User> idEq(String idString, StringJoiner errorJoiner) {
        StringJoiner idError = new StringJoiner("");
        Long id = convertStringToLong(idString, idError);
        if (id == null) {
            errorJoiner.add("Invalid value of id: '"+idError.toString()+"' couldn't convert to Long");
            return Specification.where(null);
        }
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), id);
        };
    }

    public static Specification<User> aboutContains(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("about"), "%" + word + "%");
        };
    }

    public static Specification<User> sexEq(String value, StringJoiner errorJoiner) {
        User.Sex sex;
        try {
            sex = User.Sex.valueOf(value.toUpperCase());
            return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
                return criteriaBuilder.equal(root.get("sex"), sex);
            };
        } catch (IllegalArgumentException ex) {
            errorJoiner.add("Sex named '" + value + "' is not valid");
        }
        return Specification.where(null);
    }

    public static Specification<User> youngerOrEq(String age, StringJoiner errorJoiner) {
        int value = convertStringToInt(age, errorJoiner);
        if (value == 0) {
            return Specification.where(null);
        }
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), LocalDate.now().minusYears(value + 1));
        };
    }

    public static Specification<User> olderOrEq(String age, StringJoiner errorJoiner) {
        int value = convertStringToInt(age, errorJoiner);
        if (value == 0) {
            return Specification.where(null);
        }
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), LocalDate.now().minusYears(value));
        };
    }

    private static int convertStringToInt(String ageMinString, StringJoiner errorJoiner) {
        int age = 0;
        try {
            age = Integer.parseInt(ageMinString);
        } catch (NumberFormatException ex) {
            errorJoiner.add("Value '" + ageMinString + "' could't convert to Integer");
        }
        return age;
    }

    private static Long convertStringToLong(String idString, StringJoiner errorList) {
        try {
            return Long.parseLong(idString);
        } catch (NumberFormatException ex) {
            errorList.add(idString);
        }
        return null;
    }

    public static Specification<User> usernameContains(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("username"), "%" + word + "%");
        };
    }

    public static Specification<User> usernameEqual(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("username"), word);
        };
    }

    public static Specification<User> firstNameContains(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("firstName"), "%" + word + "%");
        };
    }

    public static Specification<User> firstNameEqual(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("firstName"), word);
        };
    }

    public static Specification<User> lastNameContains(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("lastName"), "%" + word + "%");
        };
    }

    public static Specification<User> lastNameEqual(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("lastName"), word);
        };
    }

    public static Specification<User> emailContains(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("email"), "%" + word + "%");
        };
    }

    public static Specification<User> emailEqual(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("email"), word);
        };
    }

    public static Specification<User> phoneNumberContains(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("phoneNumber"), "%" + word + "%");
        };
    }

    public static Specification<User> phoneNumberEqual(String word, StringJoiner errorJoiner) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("phoneNumber"), word);
        };
    }

    public static Specification<User> statusActivityEq(String value, StringJoiner errorJoiner) {
        User.StatusActivity statusActivity;
        try {
            statusActivity = User.StatusActivity.valueOf(value.toUpperCase());
            return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
                return criteriaBuilder.equal(root.get("statusActivity"), statusActivity);
            };
        } catch (IllegalArgumentException ex) {
            errorJoiner.add("Status activity named '" + value + "' is not valid");
        }
        return Specification.where(null);
    }

    public static Specification<User> allTagIdIn(String concatTags, StringJoiner errorJoiner) {
        StringJoiner invalidLongJoiner = new StringJoiner(",");
        List<Long> arrayTags = Stream.of(concatTags.split(","))
                .map(idString -> convertStringToLong(idString, invalidLongJoiner))
                .collect(Collectors.toList());

        if (invalidLongJoiner.length() != 0) {
            errorJoiner.add("Invalid values of id " + invalidLongJoiner.toString() + " couldn't convert to Long");
            return Specification.where(null);
        }

        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            Root<User> userRoot = root;
            Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
            criteriaQuery
                    //.where(tagRoot.get("name").in(arrayTags))
                    .groupBy(userRoot.get(User_.id))
                    .having(criteriaBuilder.equal(criteriaBuilder.count(userRoot), arrayTags.size()))
                    .distinct(true);
            Expression<Collection<Tag>> tagsUsers = userRoot.get("tags");

            return criteriaBuilder.and(criteriaBuilder.in(tagRoot.get("id")).value(arrayTags),
                    criteriaBuilder.isMember(tagRoot, tagsUsers));
        };
    }

    public static Specification<User> languageIdInAndSkillGreaterOrEqual(String concatId, String skillString, StringJoiner errorJoiner) {

        //проверка и преобразование строки идентификаторов в List<Long>
        StringJoiner invalidLongJoiner = new StringJoiner(",");
        List<Long> arrayLanguages = Stream.of(concatId.split(","))
                .map(idString -> convertStringToLong(idString, invalidLongJoiner))
                .collect(Collectors.toList());

        if (invalidLongJoiner.length() != 0) {
            errorJoiner.add("Invalid values of id " + invalidLongJoiner.toString() + " couldn't convert to Long");
        }

        //проверка и преобразование строки skill в int
        int skill = convertStringToInt(skillString,errorJoiner);
        if(skill==0){
            return Specification.where(null);
        }

        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            Root<User> userRoot = root;
            Root<LanguageSkill> languageRoot = criteriaQuery.from(LanguageSkill.class);
            criteriaQuery.distinct(true);
            Expression<Collection<LanguageSkill>> languages = userRoot.get("languages");

            return criteriaBuilder.and(criteriaBuilder.in(languageRoot.get("language")).value(arrayLanguages),
                    criteriaBuilder.greaterThanOrEqualTo(languageRoot.get("value"),skill),
                    criteriaBuilder.isMember(languageRoot, languages));
        };
    }

    public static Specification<User> getAuthorsOfArticles(){
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            root.join("articles", JoinType.INNER);
            criteriaQuery
                    .distinct(true)
                    ;
            addOrderBy(Arrays.asList(User_.lastName,User_.firstName),
                    Sort.Direction.ASC,root,criteriaQuery,criteriaBuilder);
            return criteriaQuery.getRestriction();

        };
    }

    /**
     * Метод заполняет список, по которому будет производиться сортировка выводимого результата
     *
     * @param orderAttributesList - список переданных полей для сортировки
     * @param root
     * @param criteriaQuery
     * @param criteriaBuilder
     */
    private static void addOrderBy(List<SingularAttribute> orderAttributesList,
                                   Sort.Direction directionSort,
                                   Root<User> root,
                                   CriteriaQuery<?> criteriaQuery,
                                   CriteriaBuilder criteriaBuilder) {
        List<Order> orderList = new ArrayList<>(orderAttributesList.size());
        orderAttributesList.forEach(attr -> orderList.add(
                directionSort == Sort.Direction.ASC ?
                        criteriaBuilder.asc(root.get(attr)) :
                        criteriaBuilder.desc(root.get(attr))
                )
        );
        criteriaQuery.orderBy(orderList);
    }
}
