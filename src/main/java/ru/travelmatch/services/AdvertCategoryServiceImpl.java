package ru.travelmatch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.travelmatch.base.entities.AdvertCategory;
import ru.travelmatch.base.repo.AdvertCategoryRepository;

import java.util.List;

@Service
public class AdvertCategoryServiceImpl implements AdvertCategoryService {

    private AdvertCategoryRepository advertCategoryRepository;

    @Autowired
    public void setAdvertCategoryRepository(AdvertCategoryRepository advertCategoryRepository) {
        this.advertCategoryRepository=advertCategoryRepository;
    }

    public AdvertCategory findById(Long id) {
        return advertCategoryRepository.findOneById(id);
    }

    @Override
    public List<AdvertCategory> findAll() {
        return advertCategoryRepository.findAll();
    }

    @Transactional
    @Override
    public AdvertCategory save(AdvertCategory advertCategory) {
        advertCategoryRepository.save(advertCategory);
        return advertCategory;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        advertCategoryRepository.deleteById(id);
    }

}
