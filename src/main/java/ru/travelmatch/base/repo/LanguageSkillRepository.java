package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.LanguageSkill;

public interface LanguageSkillRepository extends JpaRepository<LanguageSkill, LanguageSkill.UserLanguageId> {
}
