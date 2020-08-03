package ru.travelmatch.base.repo.filters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.travelmatch.base.repo.ArticleCategoryRepository;
import ru.travelmatch.base.repo.CityRepository;
import ru.travelmatch.base.repo.LanguageRepository;
import ru.travelmatch.base.repo.TagRepository;
import ru.travelmatch.dto.*;
import ru.travelmatch.services.UserService;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 03.08.2020
 * v1.0
 * Класс для передачи настроек фильтра по статьям на front-end
 */
@Data
@Component
public class ArticleFilterSettings {
  private List<AuthorDTO> authors;
  private List<ArticleCategoryDTO> categories;
  private List<CityDTO> cities;
  private List<LanguageDTO> languages;
  private List<TagDTO> tags;

  @JsonIgnore
  private UserService userService;

  @JsonIgnore
  private ArticleCategoryRepository articleCategoryRepository;

  @JsonIgnore
  private CityRepository cityRepository;

  @JsonIgnore
  private LanguageRepository languageRepository;

  @JsonIgnore
  private TagRepository tagRepository;

  @Autowired
  public ArticleFilterSettings(UserService userService,
                               ArticleCategoryRepository articleCategoryRepository,
                               CityRepository cityRepository,
                               LanguageRepository languageRepository,
                               TagRepository tagRepository) {
    this.userService = userService;
    this.articleCategoryRepository = articleCategoryRepository;
    this.cityRepository = cityRepository;
    this.languageRepository = languageRepository;
    this.tagRepository = tagRepository;
  }

  @PostConstruct
  private void init() {
//TODO отобрать только авторов из статей
    //this.authors = userService.

    this.categories = articleCategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
            .map(category -> new ArticleCategoryDTO(category))
            .collect(Collectors.toList());
    this.cities = cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
            .map(city -> new CityDTO(city))
            .collect(Collectors.toList());
    this.languages = languageRepository.findAll().stream()
            .map(language -> new LanguageDTO(language))
            .collect(Collectors.toList());
    this.tags = tagRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
            .map(tag -> new TagDTO(tag))
            .collect(Collectors.toList());
  }
}
