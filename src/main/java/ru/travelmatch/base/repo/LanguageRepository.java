package ru.travelmatch.base.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.travelmatch.base.entities.Language;

public interface LanguageRepository extends JpaRepository<Language,Long> {
}
