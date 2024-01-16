package com.kidpix.demo.Model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ImageGeneratorDTO {

    private String kidName;
    private Long bookId ;
    private String imageInputPath;
    private String themeName;
    private List<String> storyList ;


}
