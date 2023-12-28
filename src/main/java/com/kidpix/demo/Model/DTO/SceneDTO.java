package com.kidpix.demo.Model.DTO;

import lombok.Data;

@Data
public class SceneDTO {
    private Long sceneId;
    private String scenePath;
    private String keywords;
    private  Byte pageNumber ;
    private Long categoryId;
    private String defualtStoryText ;



// Constructor, toString(), etc. (optional)
}
