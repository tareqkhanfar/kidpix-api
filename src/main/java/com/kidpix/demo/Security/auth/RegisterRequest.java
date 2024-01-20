package com.kidpix.demo.Security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
    @NotBlank(message = "First Name cannot be blank")
    @Size(max = 255, message = "First Name cannot exceed 255 characters")
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Size(max = 255, message = "Last Name cannot exceed 255 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 255, message = "Username cannot exceed 255 characters")
    private String userName;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    private String password;

    private Byte status_account =0 ;
    private Byte isAdmin =0 ;

}
