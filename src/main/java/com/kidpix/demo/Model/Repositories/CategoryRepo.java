package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {
}
