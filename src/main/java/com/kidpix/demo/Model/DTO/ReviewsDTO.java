package com.kidpix.demo.Model.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class ReviewsDTO {

    private Long reviewId;
    private Byte rating;
    private String themeName;
    private Date ratingDate ;

    private String review ;
}
