package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.Entity.AddressEntity;
import com.kidpix.demo.Model.Repositories.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepo addressRepo ;

    @Autowired
    private UserService userService ;
    public List<AddressEntity> getAllAddressByUserEmail(String email) {
      return   this.userService.getUserInfoByEmail(email).getAddressEntities() ;
    }

    public AddressEntity getAddressById (Long addressId ) {
        return this.addressRepo.findById(addressId).get() ;
    }

    public AddressEntity createAddress(AddressEntity addressEntity) {
      return  this.addressRepo.save(addressEntity) ;
    }
}
