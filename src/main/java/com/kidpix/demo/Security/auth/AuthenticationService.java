package com.kidpix.demo.Security.auth;


import com.kidpix.demo.Model.Entity.UserEntity;
import com.kidpix.demo.Model.Repositories.UserRepo;
import com.kidpix.demo.Model.Service.UserService;
import com.kidpix.demo.Security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        UserEntity userEntity = userService.getUserInfoByEmail(registerRequest.getEmail().trim());
        if (userEntity != null) {
            throw  new IllegalArgumentException("This  Account already exist ! ") ;
        }
        Byte x = 0 ;
        registerRequest.setStatus_account(x);
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
                .status_account(user.getStatus_account())
                .build();
    }

    public AuthenticationResponse authenticateRequest(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail() ,request.getPassword())
        );

         userRepository.findByEmail_(request.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("User not found")
        );

        var user = userRepository.findByEmail_(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);
        Byte status_account = this.userService.getUserInfoByEmail(request.getEmail()).getStatus_account();
        String userName = user.getUserName();
        return new AuthenticationResponse().builder().
                Token(token)
                .status_account(status_account)
                .userName(userName)
                .build();
    }

    public AuthenticationAdminResponse authenticateAdminRequest(AuthenticationRequest request) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // Retrieve user details
            var userEntity = userRepository.findByEmail_(request.getEmail()).orElseThrow(() ->
                    new UsernameNotFoundException("User not found")
            );

            // Check if the user is an admin
            if (userEntity.getIsAdmin() == 0) {
                throw new IllegalArgumentException("User is not an admin");
            }

            // Generate JWT token
            var token = jwtService.generateToken(userEntity);
            Byte isAdmin = userEntity.getIsAdmin();

            // Return response
            return new AuthenticationAdminResponse().builder()
                    .Token(token)
                    .isAdmin(isAdmin)
                    .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password", e);
        }
    }



}
