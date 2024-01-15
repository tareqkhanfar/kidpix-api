package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "reviews_tbl")
public class ReviewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "review_id")
    private Long reviewId ;


    @Column(name = "rating")
    private Byte rating;

    @Column(name = "theme_name")
    private String themeName ;

    @Column(name = "rating_date")
    private Date ratingDate ;

    @Column(name = "review_txt" , length = 2000)
    private String review ;

}
