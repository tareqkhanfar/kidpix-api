package com.kidpix.demo.Model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FinalizeStoryDTO {

        private String kidName;
        private String themeName;
        private List<String> storyList;
        private List<String> imageUrls;

        // Getters and setters


}
