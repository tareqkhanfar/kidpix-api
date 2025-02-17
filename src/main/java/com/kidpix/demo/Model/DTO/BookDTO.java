package com.kidpix.demo.Model.DTO;

import com.kidpix.demo.Model.Repositories.SceneRepo;
import lombok.Data;

import java.util.Date;

@Data
public class BookDTO {
    private Long bookId;
    private String kidName;
    private String kidPhoto;
    private String gender;
    private String notes;
    private String bookPath="na";
    private String userEmail ;
    private Integer age ;
    private String fileExtension ;
    private String status ;
   private Date createdBook ;
}
