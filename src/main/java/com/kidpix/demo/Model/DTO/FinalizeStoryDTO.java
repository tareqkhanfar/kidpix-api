package com.kidpix.demo.Model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FinalizeStoryDTO {
        private Long bookId ;
        private String kidName;
        private String themeName;
        private List<String> storyList;
        private String imageUrls;

}
