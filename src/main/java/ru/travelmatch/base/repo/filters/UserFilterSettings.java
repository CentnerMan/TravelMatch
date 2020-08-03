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
                .map(sex -> sex.name())
                .collect(Collectors.toList());
        this.statusActivity = Arrays.stream(User.StatusActivity.values())
                .map(statusActivity -> statusActivity.name())
                .collect(Collectors.toList());
        this.awards = awardRepository.findAll(Sort.by(Sort.Direction.ASC, "title")).stream()
                .map(award -> new AwardDTO(award))
                .collect(Collectors.toList());
        this.languages = languageRepository.findAll().stream()
                .map(language -> new LanguageDTO(language))
                .collect(Collectors.toList());
        this.tags = tagRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
                .map(tag -> new TagDTO(tag))
                .collect(Collectors.toList());
    }
}
