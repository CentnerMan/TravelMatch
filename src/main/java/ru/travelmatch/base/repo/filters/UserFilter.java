package ru.travelmatch.base.repo.filters;

import org.springframework.data.jpa.domain.Specification;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.base.repo.specifications.UserSpecification;

import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

public class UserFilter {
    private Specification<User> specification;

    public Specification<User> getSpecification() {
        return specification;
    }

    public UserFilter(HttpServletRequest request, StringJoiner errorJoiner) {
        specification = Specification.where(null);

        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            specification = specification.and(UserSpecification.idEq(request.getParameter("id"), errorJoiner));
        }

        if (request.getParameter("about") != null && !request.getParameter("about").isEmpty()) {
            specification = specification.and(UserSpecification.aboutContains(request.getParameter("about"), errorJoiner));
        }

        if (request.getParameter("age_min") != null && !request.getParameter("age_min").isEmpty()) {
            specification = specification.and(UserSpecification.olderOrEq(request.getParameter("age_min"), errorJoiner));
        }

        if (request.getParameter("age_max") != null && !request.getParameter("age_max").isEmpty()) {
            specification = specification.and(UserSpecification.youngerOrEq(request.getParameter("age_max"), errorJoiner));
        }

        if (request.getParameter("sex") != null && !request.getParameter("sex").isEmpty()) {
            specification = specification.and(UserSpecification.sexEq(request.getParameter("sex"), errorJoiner));
        }

        if (request.getParameter("firstname_contains") != null && !request.getParameter("firstname_contains").isEmpty()) {
            specification = specification.and(UserSpecification.firstNameContains(request.getParameter("firstname_contains"), errorJoiner));
        }

        if (request.getParameter("firstname_equal") != null && !request.getParameter("firstname_equal").isEmpty()) {
            specification = specification.and(UserSpecification.firstNameEqual(request.getParameter("firstname_equal"), errorJoiner));
        }

        if (request.getParameter("lastname_contains") != null && !request.getParameter("lastname_contains").isEmpty()) {
            specification = specification.and(UserSpecification.lastNameContains(request.getParameter("lastname_contains"), errorJoiner));
        }

        if (request.getParameter("lastname_equal") != null && !request.getParameter("lastname_equal").isEmpty()) {
            specification = specification.and(UserSpecification.lastNameEqual(request.getParameter("lastname_equal"), errorJoiner));
        }

        if (request.getParameter("email_contains") != null && !request.getParameter("email_contains").isEmpty()) {
            specification = specification.and(UserSpecification.emailContains(request.getParameter("email_contains"), errorJoiner));
        }

        if (request.getParameter("email_equal") != null && !request.getParameter("email_equal").isEmpty()) {
            specification = specification.and(UserSpecification.emailEqual(request.getParameter("email_equal"), errorJoiner));
        }

        if (request.getParameter("phone_number_contains") != null && !request.getParameter("phone_number_contains").isEmpty()) {
            specification = specification.and(UserSpecification.phoneNumberContains(request.getParameter("phone_number_contains"), errorJoiner));
        }

        if (request.getParameter("phone_number_equal") != null && !request.getParameter("phone_number_equal").isEmpty()) {
            specification = specification.and(UserSpecification.phoneNumberEqual(request.getParameter("phone_number_equal"), errorJoiner));
        }

        if (request.getParameter("username_equal") != null && !request.getParameter("username_equal").isEmpty()) {
            specification = specification.and(UserSpecification.usernameEqual(request.getParameter("username_equal"), errorJoiner));
        }

        if (request.getParameter("username_contains") != null && !request.getParameter("username_contains").isEmpty()) {
            specification = specification.and(UserSpecification.usernameContains(request.getParameter("username_contains"), errorJoiner));
        }

        if (request.getParameter("status_activity") != null && !request.getParameter("status_activity").isEmpty()) {
            specification = specification.and(UserSpecification.statusActivityEq(request.getParameter("status_activity"), errorJoiner));
        }

        if (request.getParameter("all_tag_id") != null && !request.getParameter("all_tag_id").isEmpty()) {
            specification = specification.and(UserSpecification.allTagIdIn(request.getParameter("all_tag_id"), errorJoiner));
        }

        if (request.getParameter("language_id") != null
                && !request.getParameter("language_id").isEmpty()
                && request.getParameter("language_skill") != null
                && !request.getParameter("language_skill").isEmpty()
        ) {
            specification = specification.and(
                    UserSpecification.languageIdInAndSkillGreaterOrEqual(request.getParameter("language_id"), request.getParameter("language_skill"), errorJoiner));
        }

    }
}
