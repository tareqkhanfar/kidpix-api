package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<ReviewsEntity , Long> {


    @Query("SELECT AVG(r.rating) FROM ReviewsEntity  r WHERE r.themeName = :themeName")
    Optional<Double> findAverageRatingByThemeName(String themeName);

    @Query("select  avg(r.rating) from  ReviewsEntity  r ")
    Optional<Double> findAverageOverAllWebsite();

}
