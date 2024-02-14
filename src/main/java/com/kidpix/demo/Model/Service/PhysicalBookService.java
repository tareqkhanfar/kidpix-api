package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.BookCatDTO;
import com.kidpix.demo.Model.DTO.PhysicalBookForUser;
import com.kidpix.demo.Model.DTO.PhysicalBookWithUserBook;
import com.kidpix.demo.Model.Entity.BookEntity;
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

    @Autowired
    private UserService userService ;

    @Autowired
    private EmailService emailService ;

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
          userBook.setPhysicalBookId(physicalBookEntity.getPhysicalBookId());
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

    public PhysicalBookEntity changeStatus(Long bookId, String status) {
        PhysicalBookEntity bookEntity = this.physicalBookRepo.findById(bookId).get();
        if (status.trim().equalsIgnoreCase("Ordered")){
            bookEntity.setStatus("Pending");
            emailService.NotifyStatusBook(bookEntity.getUser().getFirstName()+ " " + bookEntity.getUser().getLastName() , bookEntity.getUser().getEmail() ,"Pending" , bookEntity.getBook().getCategory().getCatName() ) ;
        }
        else if (status.trim().equalsIgnoreCase("pending")){
            bookEntity.setStatus("Shipped");
            emailService.NotifyStatusBook(bookEntity.getUser().getFirstName()+ " " + bookEntity.getUser().getLastName() , bookEntity.getUser().getEmail() ,"Shipped" , bookEntity.getBook().getCategory().getCatName() ) ;

        }
        else if (status.trim().equalsIgnoreCase("shipped")){
            bookEntity.setStatus("Delivered");
            emailService.NotifyStatusBook(bookEntity.getUser().getFirstName()+ " " + bookEntity.getUser().getLastName() , bookEntity.getUser().getEmail() ,"Delivered" , bookEntity.getBook().getCategory().getCatName() ) ;

        }
       return this.physicalBookRepo.save(bookEntity);
    }

    public List<PhysicalBookForUser> getAllBooksForUser(String email) {
        Long id =  this.userService.getUserInfoByEmail(email).getId();
        List<PhysicalBookEntity> bookEntities =  this.physicalBookRepo.getAllBooksForUser(id) ;
        List<PhysicalBookForUser>bookCatDTOS = new LinkedList<>( );

        for (PhysicalBookEntity entity : bookEntities) {
            PhysicalBookForUser dto = new PhysicalBookForUser()  ;
            dto.setBookPath(entity.getBook().getBookPath().replaceAll("/var/www/html" , "http://kid-pix.com"));
            dto.setCreatationDate(entity.getRequestDate());
            dto.setCatId(entity.getBook().getCategory().getCatID());
            dto.setBookId(entity.getPhysicalBookId());
            dto.setCatName(entity.getBook().getCategory().getCatName());
            dto.setCoverPage(entity.getBook().getCoverPage());
            dto.setKidName(entity.getBook().getKidName());
            dto.setRequestedDate(entity.getRequestDate());
            dto.setStatus(entity.getStatus());
            dto.setNumCopies(entity.getNumCopies());
            bookCatDTOS.add(dto);
        }
        return bookCatDTOS;
    }

    public String cancelBook(Long bookId) {
        this.physicalBookRepo.delete(this.physicalBookRepo.findById(bookId).get());

        return "Cancelled Successfully " ;
    }
}
