package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.StoryTextEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryTextRepo extends JpaRepository<StoryTextEntity , Long> {
}
