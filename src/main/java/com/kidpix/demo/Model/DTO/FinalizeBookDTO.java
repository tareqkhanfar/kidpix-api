package com.kidpix.demo.Model.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class FinalizeBookDTO {

    private String bookPath ;
    private String themeName ;
    private String coverPage ;
    private Long themeId ;
    private Date creationDate ;
    private String themeDescription ;


}
