package com.kidpix.demo.Model.DTO;


import lombok.Data;

@Data
public class UserAddressDTO {

    private Long addressId  ;
    private String workAddress ;
    private String homeAddress ;
    private String city ;
    private String zipCode ;
    private String country ;
    private String phone ;
}
