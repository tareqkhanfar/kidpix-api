package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.ReviewsDTO;
import com.kidpix.demo.Model.Entity.ReviewsEntity;
import com.kidpix.demo.Model.Repositories.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepo reviewRepo ;

    public ReviewsEntity sendReview(ReviewsEntity reviewsEntity) {

      return   this.reviewRepo.save(reviewsEntity);

    }

    public List<ReviewsEntity> getAllReviews() {

      return this.reviewRepo.findAll();
    }

    public Double getAvgRatingTotalByThemeName(String themeName) {
  return  this.reviewRepo.findAverageRatingByThemeName(themeName) .get();
    }

    public Double getAverageOverAllKidpix() {

        return  this.reviewRepo.findAverageOverAllWebsite().get();
    }
}
