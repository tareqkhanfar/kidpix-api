package com.kidpix.demo.Security.auth;


import com.kidpix.demo.Model.DTO.UserDTO;
import com.kidpix.demo.Security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

private final AuthenticationService authenticationService;


@Autowired
private JwtService jwtService ;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {

        System.out.println(registerRequest.getEmail());
         return ResponseEntity.ok(authenticationService.register(registerRequest));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateRequest(@RequestBody AuthenticationRequest Request) {
        return ResponseEntity.ok(authenticationService.authenticateRequest(Request));


    }

    @PostMapping("/authenticate/adminLogin")
    public ResponseEntity<AuthenticationAdminResponse> authenticateAdminRequest(@RequestBody AuthenticationRequest Request) {
        return ResponseEntity.ok(authenticationService.authenticateAdminRequest(Request));
    }





}
