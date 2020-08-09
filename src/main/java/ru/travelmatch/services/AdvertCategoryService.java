package ru.travelmatch.services;

import ru.travelmatch.base.entities.AdvertCategory;

import java.util.List;

public interface AdvertCategoryService {
    List<AdvertCategory> findAll();
    AdvertCategory findById(Long id);
    AdvertCategory save(AdvertCategory advertCategory);
    void deleteById(Long id);
}
