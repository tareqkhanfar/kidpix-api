package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.PhysicalBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhysicalBookRepo extends JpaRepository<PhysicalBookEntity , Long> {

    @Query("SELECT COUNT(DISTINCT p.user) FROM PhysicalBookEntity p")
    Long countDistinctUser();

    @Query("select  sum (p.numCopies) from PhysicalBookEntity  p")
    Long getTotalCopies();

    @Query("SELECT SUM(p.numCopies) FROM PhysicalBookEntity p WHERE FUNCTION('MONTH', p.requestDate) = FUNCTION('MONTH', CURRENT_DATE) AND FUNCTION('YEAR', p.requestDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Double findTotalSalesForCurrentMonth();

    @Query("SELECT SUM(p.numCopies) FROM PhysicalBookEntity p WHERE FUNCTION('YEAR', p.requestDate) = FUNCTION('YEAR', CURRENT_DATE)")
    Double findTotalSalesForCurrentYear();

    @Query("select count (*) from PhysicalBookEntity p where p.status= :status")
    Long findAllByStatus(String status) ;


    @Query("SELECT b from PhysicalBookEntity b where b.user.id = :userId")
    List<PhysicalBookEntity> getAllBooksForUser( Long userId);

    @Query("delete from PhysicalBookEntity  p where  p.PhysicalBookId =:bookId")
    void deletePhysicalBook (Long bookId);
}
