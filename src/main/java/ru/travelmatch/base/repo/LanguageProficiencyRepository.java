package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.LanguageProficiency;

public interface LanguageProficiencyRepository extends JpaRepository<LanguageProficiency,LanguageProficiency.UserLanguageId> {
}
