package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<AddressEntity , Long> {
}
