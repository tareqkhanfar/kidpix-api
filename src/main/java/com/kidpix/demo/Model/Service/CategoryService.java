package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.Entity.CategoryEntity;
import com.kidpix.demo.Model.Repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {


    @Autowired
    private CategoryRepo categoryRepo ;


    public List<CategoryEntity> findAllThemes() {
        return this.categoryRepo.findAll();
    }
}
