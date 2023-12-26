package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.ApiResponse;
import com.kidpix.demo.Model.DTO.UserDTO;
import com.kidpix.demo.Model.Entity.UserEntity;
import com.kidpix.demo.Model.Service.EmailService;
import com.kidpix.demo.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")

public class UserController {
@Autowired
    private UserService userService ;

@PostMapping("/signup")
    public ResponseEntity<Object> signUP (@Validated @RequestBody UserDTO userDTO) {
try {


    UserDTO dto = toDTO(this.userService.signUP(userDTO));
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
}
catch (IllegalArgumentException e ) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

}
catch (Exception exception ) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
}
}

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated @RequestBody UserDTO userDTO) {
    try {


        UserDTO userEntity =toDTO(userService.login(userDTO));
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
    catch (IllegalArgumentException e  ){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    catch (Exception exception ) {

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/getUserInfoByEmail")
    public ResponseEntity<Object> getUserInfoByEmail(@RequestParam String email) {
    try {
        UserDTO userEntity = toDTO(userService.getUserInfoByEmail(email));
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
    catch (IllegalArgumentException e  ){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    catch (Exception exception ) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @Autowired
    private EmailService emailService;
    @CrossOrigin(origins = "http://localhost:8943")
    @PostMapping("/sendSecurityCode")
    public ResponseEntity<ApiResponse> processRequest(@RequestBody Map<String, String> requestData) {
        try {
            emailService.sendSecurityCode(requestData.get("email").trim());
            return ResponseEntity.ok(new ApiResponse("Code sent Successfully, Check your Email", true));
        }
        catch (Exception e ) {
            return ResponseEntity.internalServerError().body(new ApiResponse(e.getMessage(), false));
        }

    }
    @CrossOrigin(origins = "http://localhost:8943") // Specify the allowed origin

    @PostMapping("/validateCode")
    public ResponseEntity<ApiResponse> validateSecurityCode(@RequestBody Map<String, String> requestData) {
        boolean isValid = emailService.validateSecurityCode(requestData.get("email"), Integer.parseInt(requestData.get("securityCode").trim()));

        if (isValid) {
            return ResponseEntity.ok(new ApiResponse("Your Email is verified successfully", true));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse("Your Email is NOT verified successfully", false));
        }
    }


    private UserDTO toDTO(UserEntity save) {
        UserDTO dto = new UserDTO();
        dto.setEmail(save.getEmail());
        dto.setFirstName(save.getFirstName());
        dto.setUserName(save.getUserName());
        dto.setPassword(save.getPassword());
        dto.setId(save.getId());
        dto.setLastName(save.getLastName());
        dto.setStatus_account(save.getStatus_account());
        return dto;
    }

}
