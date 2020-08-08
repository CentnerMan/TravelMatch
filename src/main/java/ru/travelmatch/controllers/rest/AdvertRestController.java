package ru.travelmatch.controllers.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.travelmatch.base.repo.filters.ArticleFilter;
import ru.travelmatch.base.repo.filters.ArticleFilterSettings;
import ru.travelmatch.base.repo.filters.UserFilter;
import ru.travelmatch.base.repo.filters.UserFilterSettings;
import ru.travelmatch.dto.AdvertDto;
import ru.travelmatch.dto.ArticleSimpleDTO;
import ru.travelmatch.dto.UserSimpleDTO;
import ru.travelmatch.services.AdvertService;
import ru.travelmatch.services.ArticleService;
import ru.travelmatch.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/adverts")
@Api("Set of endpoints for adverts")
@AllArgsConstructor
public class AdvertRestController {
    private AdvertService advertService;

    @GetMapping
    @ApiOperation("Return list of adverts")
    public List<AdvertDto> getAdvertsList() {
        return advertService.getAllDtos();
    }
}
