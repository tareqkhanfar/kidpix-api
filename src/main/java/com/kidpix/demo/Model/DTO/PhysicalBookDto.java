package com.kidpix.demo.Model.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class PhysicalBookDto
{
    private Long physicalBookId;
    private Integer numCopies;
    private Date requestDate;
    private String status;
    private Long userId;
    private Long bookId;
}
