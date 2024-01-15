package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.UserDTO;
import com.kidpix.demo.Model.Entity.UserEntity;
import com.kidpix.demo.Model.Repositories.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

   @Autowired
    private UserRepo userRepo ;

    public UserEntity signUP(UserDTO userDTO) {


        UserEntity userEntity = userRepo.findByEmail(userDTO.getEmail().trim());
        if (userEntity != null) {
            throw  new IllegalArgumentException("This  Account already exist ! ") ;
        }
         userEntity = new UserEntity();
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setPassword(userDTO.getPassword());
        Byte x = 0 ;
        userEntity.setStatus_account(x);
        return this.userRepo.save(userEntity);

    }



    public UserEntity login(UserDTO userDTO) throws IllegalArgumentException {

        UserEntity userEntity = this.userRepo.findByEmail(userDTO.getEmail());

        if (userEntity == null || !userDTO.getPassword().equals(userEntity.getPassword()) || !userDTO.getEmail().equals(userEntity.getEmail())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return userEntity;
    }
    public UserEntity Adminlogin(UserDTO userDTO) throws IllegalArgumentException {

        UserEntity userEntity = this.userRepo.findByEmail(userDTO.getEmail());

        if (userEntity == null || !userDTO.getPassword().equals(userEntity.getPassword()) || !userDTO.getEmail().equals(userEntity.getEmail())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (userEntity.getIsAdmin() != 0){
            return userEntity ;
        }
        else {
            throw new IllegalArgumentException("Invalid username or password");

        }

    }

    public UserEntity getUserInfoByEmail(String email) {
        UserEntity userEntity = this.userRepo.findByEmail(email);
        if (userEntity == null) {
            throw new IllegalArgumentException("User not found with this email: " + email);
        }
        return userEntity;
    }

    public boolean insertSecurityCodeForUserByEmail(String email , Integer code) {
        UserEntity userEntity = userRepo.findByEmail(email) ;
        userEntity.setSecurity_code(code);
        userRepo.save(userEntity) ;
        return true ;
    }

    public void save (UserEntity userEntity ) {
        userRepo.save(userEntity) ;
    }

    public List<UserEntity> getAllUsers() {
        return  this.userRepo.findAll();
    }

    public UserEntity getUserById(Long userId) {
        return this.userRepo.findById(userId).get();
    }
}
