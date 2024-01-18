package com.kidpix.demo.Model.DTO;


import lombok.Data;

@Data
public class HighestLowestRatingDTO {
    private String themeName ;
    private Double rating;


    public HighestLowestRatingDTO(String str , Double rating){
        this.themeName = str ;
        this.rating = rating ;
    }
}
