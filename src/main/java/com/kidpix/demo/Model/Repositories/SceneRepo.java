package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.CategoryEntity;
import com.kidpix.demo.Model.Entity.SceneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SceneRepo extends JpaRepository<SceneEntity , Long> {

    List<SceneEntity> findByCategory_CatID(Long catID);
}
