package com.kidpix.demo.Model.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class UserDTO {

    private Long id;

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


    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Byte getStatus_account() {
        return status_account;
    }

    public void setStatus_account(Byte status_account) {
        this.status_account = status_account;
    }

    // getters and setters

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}