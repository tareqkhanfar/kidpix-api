package com.kidpix.demo.Model.DTO;


import lombok.Data;

@Data
public class HighestLowestRatingDTO {
    private String themeName = "N\\A" ;
    private Double rating=0.0;


    public HighestLowestRatingDTO(String str , Double rating){
        this.themeName = str ;
        this.rating = rating ;
    }
}
