package ru.travelmatch.base.repo.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.travelmatch.base.entities.User;
import ru.travelmatch.base.repo.AwardRepository;
import ru.travelmatch.base.repo.LanguageRepository;
import ru.travelmatch.base.repo.TagRepository;
import ru.travelmatch.dto.AwardDTO;
import ru.travelmatch.dto.LanguageDTO;
import ru.travelmatch.dto.TagDTO;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * Класс для передачи настроек фильтра пользователей для front-end.
 * v1.0
 * Фильтр передает следующие настройки:
 *      sex - список возможных полов
 *      statusActivity - список возможных состояний пользователя (автивен, забанен и т.п.)
 *      awards - отсортированный список возможных наград из {@link ru.travelmatch.base.entities.Award}
 *      languages - список из {@link ru.travelmatch.base.entities.Language}
 *      tags - отсортированный список по названию из {@link ru.travelmatch.base.entities.Tag}
 */
@Data
@Component
public class UserFilterSettings implements Serializable {

    private List<String> sex;
    private List<String> statusActivity;
    private List<AwardDTO> awards;
    private List<LanguageDTO> languages;
    private List<TagDTO> tags;

    @JsonIgnore
    private AwardRepository awardRepository;

    @JsonIgnore
    private LanguageRepository languageRepository;

    @JsonIgnore
    private TagRepository tagRepository;

    @Autowired
    public UserFilterSettings(AwardRepository awardRepository, LanguageRepository languageRepository, TagRepository tagRepository) {
        this.awardRepository = awardRepository;
        this.languageRepository = languageRepository;
        this.tagRepository = tagRepository;
    }

    @PostConstruct
    private void init() {
        this.sex = Arrays.stream(User.Sex.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        this.statusActivity = Arrays.stream(User.StatusActivity.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        this.awards = awardRepository.findAll(Sort.by(Sort.Direction.ASC, "title")).stream()
                .map(AwardDTO::new)
                .collect(Collectors.toList());
        this.languages = languageRepository.findAll().stream()
                .map(LanguageDTO::new)
                .collect(Collectors.toList());
        this.tags = tagRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
                .map(TagDTO::new)
                .collect(Collectors.toList());
    }
}
