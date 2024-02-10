package com.kidpix.demo.Model.DTO;

import lombok.Data;

import java.util.Date;
@Data
public class PhysicalBookForUser {
    private Long bookId ;
    private Long catId ;
    private String bookPath ;
    private Date creatationDate ;
    private String catName ;
    private String themePath ;
    private String status ;
    private Date requestedDate ;
    private Integer numCopies ;
}
