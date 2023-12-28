package com.kidpix.demo.Model.DTO;


import lombok.Data;

@Data
public class StoryTextDTO {
    private Long storyId;
    private Long bookId; // Assuming you want to represent the relationship by ID
    private String storyText;

}
