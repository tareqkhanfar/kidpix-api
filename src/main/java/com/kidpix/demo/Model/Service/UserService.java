package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.UserDTO;
import com.kidpix.demo.Model.Entity.UserEntity;
import com.kidpix.demo.Model.Repositories.UserRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   @Autowired
    private UserRepo userRepo ;

    public UserDTO signUP(UserDTO userDTO) {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setUserName(userDTO.getUserName());
        userEntity.setPassword(userDTO.getPassword());
        return toDTO(this.userRepo.save(userEntity));

    }

    private UserDTO toDTO(UserEntity save) {
        UserDTO dto = new UserDTO();
        dto.setEmail(save.getEmail());
        dto.setFirstName(save.getFirstName());
        dto.setUserName(save.getUserName());
        dto.setPassword(save.getPassword());
        dto.setId(save.getId());
        dto.setLastName(save.getLastName());
        return dto;
    }

    public UserDTO login(UserDTO userDTO) {
        System.out.println( "-->" + userDTO.getUserName());

        UserEntity userEntity = this.userRepo.findByUserName(userDTO.getUserName());
        System.out.println("username : " + userEntity.getUserName() + "-->" + userDTO.getUserName());
        if (userEntity == null || !userDTO.getPassword().equals(userEntity.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        return toDTO(userEntity);
    }

    public UserDTO getUserInfoByEmail(String email) {
        UserEntity userEntity = this.userRepo.findByEmail(email);
        if (userEntity == null) {
            throw new RuntimeException("User not found with this email: " + email);
        }
        return toDTO(userEntity);
    }

}
