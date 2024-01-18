package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.HighestLowestRatingDTO;
import com.kidpix.demo.Model.DTO.ReviewsDTO;
import com.kidpix.demo.Model.Entity.ReviewsEntity;
import com.kidpix.demo.Model.Repositories.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class ReviewService {


    @Autowired
    private ReviewRepo reviewRepo ;

    public ReviewsEntity sendReview(ReviewsEntity reviewsEntity) {
        reviewsEntity.setRatingDate(new Date());
      return   this.reviewRepo.save(reviewsEntity);

    }

    public List<ReviewsEntity> getAllReviews() {

      return this.reviewRepo.findAll();
    }

    public Double getAvgRatingTotalByThemeName(String themeName) {
  return  this.reviewRepo.findAverageRatingByThemeName(themeName) .get();
    }

    public Double getAverageOverAllKidpix() {

        return Math.round(this.reviewRepo.findAverageOverAllWebsite().get() * 100.0) / 100.0;
    }

    public HighestLowestRatingDTO getHighestRating() {
        List<Object[]> averages = reviewRepo.findAverageRatingsByTheme();
        return averages.stream()
                .max(Comparator.comparing(avg -> (Double) avg[1]))
                .map(result -> new HighestLowestRatingDTO((String) result[0], (Double) result[1]))
                .orElse(null); // handle the case where no result is found
    }

    public HighestLowestRatingDTO getLowestRating() {
        List<Object[]> averages = reviewRepo.findAverageRatingsByTheme();
        return averages.stream()
                .min(Comparator.comparing(avg -> (Double) avg[1]))
                .map(result -> new HighestLowestRatingDTO((String) result[0], (Double) result[1]))
                .orElse(null); // handle the case where no result is found
    }








}
