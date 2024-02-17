package com.kidpix.demo.Security.auth;


import com.kidpix.demo.Model.DTO.UserDTO;
import com.kidpix.demo.Model.Service.UserService;
import com.kidpix.demo.Security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

private final AuthenticationService authenticationService;


@Autowired
private JwtService jwtService ;

@Autowired
private UserService userService ;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {

        System.out.println(registerRequest.getEmail());
        try {
            return ResponseEntity.ok(authenticationService.register(registerRequest));
        }
        catch (Exception e ){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateRequest(@RequestBody AuthenticationRequest Request) {
        try {
            return ResponseEntity.ok(authenticationService.authenticateRequest(Request));

        }
        catch (Exception exception ) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }


    }

    @PostMapping("/authenticate/adminLogin")
    public ResponseEntity<AuthenticationAdminResponse> authenticateAdminRequest(@RequestBody AuthenticationRequest Request) {
        return ResponseEntity.ok(authenticationService.authenticateAdminRequest(Request));
    }


    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String , Object> map) {
        try {

            boolean flag = this.authenticationService.changePassword((String) map.get("email"), (String) map.get("password"), (Integer) map.get("securityCode"));

            if (flag) {
                return ResponseEntity.ok("Password has been Changed Successfully .");
            } else {
                return ResponseEntity.badRequest().body("User Not Found .");
            }
        }catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage()) ;
        }
    }



}
