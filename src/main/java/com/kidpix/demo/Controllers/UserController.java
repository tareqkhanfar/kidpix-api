package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.UserDTO;
import com.kidpix.demo.Model.Entity.UserEntity;
import com.kidpix.demo.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
@Autowired
    private UserService userService ;

@PostMapping("/signup")
    public ResponseEntity<Object> signUP (@Validated @RequestBody UserDTO userDTO) {
try {


    UserDTO dto = this.userService.signUP(userDTO);
    return new ResponseEntity<>(dto, HttpStatus.CREATED);
}
catch (Exception exception ) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
}
}

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated @RequestBody UserDTO userDTO) {
    try {


        UserDTO userEntity = userService.login(userDTO);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
    catch (Exception exception ) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    }

    @GetMapping("/getUserInfoByEmail")
    public ResponseEntity<Object> getUserInfoByEmail(@RequestParam String email) {
    try {
        UserDTO userEntity = userService.getUserInfoByEmail(email);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
    catch (Exception exception ) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
    }



}
