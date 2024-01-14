package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.PhysicalBookWithUserBook;
import com.kidpix.demo.Model.Entity.PhysicalBookEntity;
import com.kidpix.demo.Model.Repositories.PhysicalBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PhysicalBookService {


    @Autowired
    private PhysicalBookRepo physicalBookRepo ;
    public PhysicalBookEntity createPhysicalBook(PhysicalBookEntity entity) {
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
}
