package com.kidpix.demo.Model.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class ResponseDigitalBooksDTO {

    private String username ;
    private String email ;
    private Date createdBook ;
    private String themeName ;


}
