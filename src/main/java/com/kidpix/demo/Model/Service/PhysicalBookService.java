package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.PhysicalBookWithUserBook;
import com.kidpix.demo.Model.Entity.PhysicalBookEntity;
import com.kidpix.demo.Model.Repositories.PhysicalBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class PhysicalBookService {


    @Autowired
    private PhysicalBookRepo physicalBookRepo ;
    public PhysicalBookEntity createPhysicalBook(PhysicalBookEntity entity) {
        entity.setStatus("Pending");
        entity.setRequestDate(new Date());
       return this.physicalBookRepo.save(entity);
    }

    public List<PhysicalBookWithUserBook> getPhysicalBooks() {
      List<PhysicalBookEntity> physicalBookEntities =   this.physicalBookRepo.findAll();
      List<PhysicalBookWithUserBook> physicalBookWithUserBooks = new LinkedList<>() ;
      for (PhysicalBookEntity physicalBookEntity : physicalBookEntities){
          PhysicalBookWithUserBook userBook = new PhysicalBookWithUserBook();
          userBook.setThemeName(physicalBookEntity.getBook().getCategory().getCatName());
          userBook.setUserName(physicalBookEntity.getUser().getUserName());
          userBook.setNumCopies(physicalBookEntity.getNumCopies());
          userBook.setUserEmail(physicalBookEntity.getUser().getEmail());
          userBook.setRequestDate(physicalBookEntity.getRequestDate());
          userBook.setStatusBook(physicalBookEntity.getStatus());
          physicalBookWithUserBooks.add(userBook) ;

      }
      return physicalBookWithUserBooks ;
     }

    public Long getTotalClientPurchesBook() {
       return this.physicalBookRepo.countDistinctUser();
    }

    public Double getTotalSales() {
        return  this.physicalBookRepo.getTotalCopies()*5.0 ;
    }

    public Double getTotalSalesForCurrentMonth() {
        return this.physicalBookRepo.findTotalSalesForCurrentMonth() * 5.0 ;
    }

    public Long findBookByStatus(String status) {
        return this.physicalBookRepo.findAllByStatus(status);
    }

    public Double getTotalSalesForCurrentYear() {
        return this.physicalBookRepo.findTotalSalesForCurrentYear() * 5.0;
    }
}
