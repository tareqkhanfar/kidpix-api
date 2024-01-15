package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT COUNT(*) FROM BookEntity p WHERE FUNCTION('MONTH', p.createdBook) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', p.createdBook) = FUNCTION('YEAR', CURRENT_DATE)")
    Long findTotalSalesForCurrentMonth();

    @Query("SELECT COUNT(*) FROM BookEntity p WHERE FUNCTION('YEAR', p.createdBook) = FUNCTION('YEAR', CURRENT_DATE)")
    Long findTotalSalesForCurrentYear();
    @Query("SELECT COUNT(DISTINCT p.user) FROM BookEntity p")
    Long totalUsersPurchesBook() ;
}