package ru.travelmatch.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.travelmatch.base.entities.AdvertCategory;
import ru.travelmatch.services.AdvertCategoryService;

import java.util.List;

/**
 * GeekBrains Java, TravelMatch.
 *
 * @author Maria Glazova
 * @version 1.0.0 13.06.2020
 * @link https://github.com/dartusha
 */

@RestController
@RequestMapping(value = "/api/v1/advcategory")
public class AdvertCategoryRestController {

    private AdvertCategoryService advertCategoryService;

    @Autowired
    public void setAdvertCategoryService(AdvertCategoryService advertCategoryService) {
        this.advertCategoryService=advertCategoryService;
    }

    @GetMapping
    public List<AdvertCategory> getAdverts() throws Exception {
        try {
            List<AdvertCategory> advertCategoriesList = advertCategoryService.findAll();
            return advertCategoriesList;

        } catch (Exception e) {
            throw new Exception("Something wrong");
        }
    }

    @GetMapping("/{id}")
    public AdvertCategory getAdvertById(Long id) throws Exception {
        try {
            AdvertCategory advertCategory=advertCategoryService.findById(id);
            return advertCategory;

        } catch (Exception e) {
            throw new Exception("Something wrong");
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertCategory addAdvert(@RequestBody AdvertCategory advertCategory) throws Exception {
        try {
            advertCategoryService.save(advertCategory);
            return advertCategory;

        } catch (Exception e) {
            throw new Exception("Something wrong");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        advertCategoryService.deleteById(id);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public AdvertCategory updateAdvert(@RequestBody AdvertCategory advertCategory) throws Exception {
        try {
            return advertCategoryService.save(advertCategory);

        } catch (Exception e) {
            throw new Exception("Something wrong");
        }
    }

}
