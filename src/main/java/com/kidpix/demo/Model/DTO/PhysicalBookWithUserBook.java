package com.kidpix.demo.Model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PhysicalBookWithUserBook {

    private String userName ;
    private String userEmail;


    private String ThemeName ;

    private Integer numCopies ;

    private Date requestDate ;

    private String statusBook ;

}
