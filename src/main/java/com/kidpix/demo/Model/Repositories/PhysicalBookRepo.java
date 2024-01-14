package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.PhysicalBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicalBookRepo extends JpaRepository<PhysicalBookEntity , Long> {
}
