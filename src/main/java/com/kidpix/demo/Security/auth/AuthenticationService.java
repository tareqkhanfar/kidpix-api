package com.kidpix.demo.Security.auth;


import com.kidpix.demo.Model.Entity.UserEntity;
import com.kidpix.demo.Model.Repositories.UserRepo;
import com.kidpix.demo.Model.Service.UserService;
import com.kidpix.demo.Security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepository ;

    private final PasswordEncoder passwordEncoder ;

    private final JwtService jwtService ;

    private final AuthenticationManager authenticationManager ;

    @Autowired
    private UserService userService ;
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = UserEntity.builder()
                .userName(registerRequest.getUserName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isAdmin(registerRequest.getIsAdmin())
                .lastName(registerRequest.getLastName())
                .firstName(registerRequest.getFirstName())
                .status_account(registerRequest.getStatus_account())
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse().builder().
                Token(token)
                .build();
    }

    public AuthenticationResponse authenticateRequest(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail() ,request.getPassword())
        );
        var user = userRepository.findByEmail_(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);
        Byte status_account = this.userService.getUserInfoByEmail(request.getEmail()).getStatus_account();
        return new AuthenticationResponse().builder().
                Token(token)
                .status_account(status_account)
                .build();
    }


}
