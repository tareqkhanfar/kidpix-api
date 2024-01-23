package com.kidpix.demo.Model.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class PhysicalBookDto
{
    private Integer numCopies;
    private Long bookId;
    private Long addressId ;
}
