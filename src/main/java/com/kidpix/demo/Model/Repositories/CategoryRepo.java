package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {



    @Query("select c.scribus_path from CategoryEntity c  where c.catName=:themeName")
    String findByNameCatName(String themeName);

    @Query("select c.ThemeDirPath from CategoryEntity c  where c.catName=:themeName")
    String findThemePathByCatName(String themeName);
}
