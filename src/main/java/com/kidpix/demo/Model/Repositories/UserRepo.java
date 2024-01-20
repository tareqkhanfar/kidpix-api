package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity , Long> {
    UserEntity findByUserName(String userName);
    Optional<UserEntity> findByEmail_(String email);
    UserEntity findByEmail(String email);


}
