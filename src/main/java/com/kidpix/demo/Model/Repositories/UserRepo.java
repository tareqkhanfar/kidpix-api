package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity , Long> {
    UserEntity findByUserName(String userName);
    UserEntity findByEmail(String email);


}
