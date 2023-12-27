package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.SceneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SceneRepo extends JpaRepository<SceneEntity , Long> {

    @Query(value = "SELECT cat.cat_name, s.scene_path, b.book_path " +
            "FROM scene_tbl s " +
            "JOIN book_scene bs ON (s.scene_id = bs.scene_id) " +
            "JOIN book_tbl b ON (b.book_id = bs.book_id) " +
            "JOIN category_tbl cat ON (cat.cat_id = s.cat_id) " +
            "WHERE s.cat_id = 0", nativeQuery = true)
    List<Object[]> findAllThemesRaw();
}
