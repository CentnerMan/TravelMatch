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
import ru.travelmatch.base.repo.specifications.UserSpecification;
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
 * Фильтр передает следующие настройки:
 *      authors - список авторов статей (id, firstname,lastname), отсортированный по lastname и firstname
 *          Авторы - это заполненные значения полей author в {@link ru.travelmatch.base.entities.Article};
 *      categories - отсортированный список из {@link ru.travelmatch.base.entities.ArticleCategory};
 *      cities - отсортированный список из {@link ru.travelmatch.base.entities.City};
 *      languages - список из {@link ru.travelmatch.base.entities.Language}
 *      tags - отсортированный список по названию из {@link ru.travelmatch.base.entities.Tag}
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
    this.authors = userService.findAll(UserSpecification.getAuthorsOfArticles()).stream()
            .map(AuthorDTO::new)
            .collect(Collectors.toList());
    this.categories = articleCategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
            .map(ArticleCategoryDTO::new)
            .collect(Collectors.toList());
    this.cities = cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
            .map(CityDTO::new)
            .collect(Collectors.toList());
    this.languages = languageRepository.findAll().stream()
            .map(LanguageDTO::new)
            .collect(Collectors.toList());
    this.tags = tagRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).stream()
            .map(TagDTO::new)
            .collect(Collectors.toList());
  }
}
