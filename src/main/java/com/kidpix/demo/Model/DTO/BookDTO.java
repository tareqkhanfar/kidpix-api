package com.kidpix.demo.Model.DTO;

import lombok.Data;

@Data
public class BookDTO {
    private Long bookId;
    private String kidName;
    private String kidPhoto;
    private String gender;
    private String notes;
    private String bookPath;
    private String userEmail ;
}
