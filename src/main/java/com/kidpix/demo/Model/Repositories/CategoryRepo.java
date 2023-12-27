package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.CatrgoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CatrgoryEntity , Long> {
}
