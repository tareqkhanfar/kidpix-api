package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}