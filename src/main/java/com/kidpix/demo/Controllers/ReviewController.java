package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.HighestLowestRatingDTO;
import com.kidpix.demo.Model.DTO.ReviewsDTO;
import com.kidpix.demo.Model.Entity.ReviewsEntity;
import com.kidpix.demo.Model.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping( "/api/reviews")
public class ReviewController {


    @Autowired
    private ReviewService reviewService ;



    @PostMapping
    public ResponseEntity<?>sendReview(@RequestBody ReviewsDTO reviewsDTO) {
        try{
           return ResponseEntity.ok(convertToDto(this.reviewService.sendReview(convertToEntity(reviewsDTO))));
        }
        catch (Exception exception ) {
           return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping("/getHighestRating")
    public ResponseEntity<HighestLowestRatingDTO>getHighestRating() {
        return ResponseEntity.ok(this.reviewService.getHighestRating());
    }
    @GetMapping("/getLowestRating")
    public ResponseEntity<HighestLowestRatingDTO>getLowestRating() {
        return ResponseEntity.ok(this.reviewService.getLowestRating());
    }


    @GetMapping("/getAllReviews")
    public ResponseEntity<List<ReviewsDTO >> getAllReview () {
        List<ReviewsDTO >allReviews =   this.reviewService.getAllReviews().stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(allReviews);
    }

    @GetMapping("/getAvgRatingTotal/{themeName}")
    public ResponseEntity<Double> getAvgRatingTotal(@PathVariable(name = "themeName") String themeName) {
  return   ResponseEntity.ok(this.reviewService.getAvgRatingTotalByThemeName(themeName)) ;
    }

    @GetMapping("/getAvgOverAllWebsite")

    public ResponseEntity<Double>getAvgRatingOverAllKidpix(){
       return ResponseEntity.ok(this.reviewService.getAverageOverAllKidpix());
    }



    public ReviewsDTO convertToDto(ReviewsEntity entity) {
        ReviewsDTO dto = new ReviewsDTO();
        dto.setReviewId(entity.getReviewId());
        dto.setRating(entity.getRating());
        dto.setThemeName(entity.getThemeName());
        dto.setRatingDate(entity.getRatingDate());
        dto.setReview(entity.getReview());
        return dto;
    }
    public ReviewsEntity convertToEntity(ReviewsDTO dto) {
        ReviewsEntity entity = new ReviewsEntity();
        entity.setReviewId(dto.getReviewId());
        entity.setRating(dto.getRating());
        entity.setThemeName(dto.getThemeName());
        entity.setRatingDate(dto.getRatingDate());
        entity.setReview(dto.getReview());
        return entity;
    }



}
