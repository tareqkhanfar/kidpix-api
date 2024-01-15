package com.kidpix.demo.Model.DTO;


import lombok.Data;

@Data
public class ReviewsDTO {

    private Long reviewId;
    private Byte rating;
    private String themeName;
}
