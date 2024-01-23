package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.UserAddressDTO;
import com.kidpix.demo.Model.Entity.AddressEntity;
import com.kidpix.demo.Model.Service.AddressService;
import com.kidpix.demo.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService ;

    @Autowired
    private UserService userService ;

    @GetMapping("/getAllAddressByUserEmail/{email}")
    public ResponseEntity<List<UserAddressDTO>>getAllAddressByUserEmail(@PathVariable("email") String email){
       List<UserAddressDTO>  userAddressDTOS =  this.addressService.getAllAddressByUserEmail(email ) .stream().map(this::convertEntityToDTO).collect(Collectors.toList());
       return ResponseEntity.ok(userAddressDTOS);
    }
    @PostMapping("/create")
    public ResponseEntity<UserAddressDTO> createAddress(@RequestBody  UserAddressDTO addressDTO){
        AddressEntity entity = this.addressService.createAddress(convertDTOToEntity(addressDTO));
        return  ResponseEntity.ok(convertEntityToDTO(entity));
    }

    public UserAddressDTO convertEntityToDTO(AddressEntity addressEntity) {
        UserAddressDTO dto = new UserAddressDTO();
        dto.setAddressId(addressEntity.getAddress_id());
        dto.setWorkAddress(addressEntity.getWork_address());
        dto.setHomeAddress(addressEntity.getHome_address());
        dto.setCity(addressEntity.getCity());
        dto.setCountry(addressEntity.getCountry());
        dto.setZipCode(addressEntity.getZipCode());
        dto.setPhone(addressEntity.getPhone());
        dto.setEmail(addressEntity.getUser().getEmail());
        return dto;
    }

    public AddressEntity convertDTOToEntity(UserAddressDTO dto) {
        return AddressEntity.builder()
                .user(this.userService.getUserInfoByEmail(dto.getEmail()))
                .address_id(dto.getAddressId())
                .work_address(dto.getWorkAddress())
                .home_address(dto.getHomeAddress())
                .city(dto.getCity())
                .country(dto.getCountry())
                .zipCode(dto.getZipCode())
                .phone(dto.getPhone())
                .build();
    }



}
