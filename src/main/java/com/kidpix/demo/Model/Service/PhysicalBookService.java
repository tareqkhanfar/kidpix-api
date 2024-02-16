package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.DTO.BookCatDTO;
import com.kidpix.demo.Model.DTO.PhysicalBookForUser;
import com.kidpix.demo.Model.DTO.PhysicalBookWithUserBook;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.CategoryEntity;
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
          BookEntity book = physicalBookEntity.getBook() ;
          if (book == null) {
              continue;
          }
          CategoryEntity category = book.getCategory() ;
          if (category != null) {
              userBook.setPhysicalBookId(physicalBookEntity.getPhysicalBookId());
              userBook.setThemeName(category.getCatName());
              userBook.setUserName(physicalBookEntity.getUser().getUserName());
              userBook.setNumCopies(physicalBookEntity.getNumCopies());
              userBook.setUserEmail(physicalBookEntity.getUser().getEmail());
              userBook.setRequestDate(physicalBookEntity.getRequestDate());
              userBook.setStatusBook(physicalBookEntity.getStatus());
              physicalBookWithUserBooks.add(userBook) ;
          }

      }
      return physicalBookWithUserBooks ;
     }

    public Long getTotalClientPurchesBook() {
        Long value = this.physicalBookRepo.countDistinctUser() ;
       return value!=null ? value : 0;
    }

    public Double getTotalSales() {
        Long totalCopies = this.physicalBookRepo.getTotalCopies();
        return totalCopies != null ? totalCopies * 5.0 : 0.0;
    }


    public Double getTotalSalesForCurrentMonth() {
        Double value =  this.physicalBookRepo.findTotalSalesForCurrentMonth() ;
        if (value != null){
            return  value.doubleValue() * 5 ;
        }
        else {
            return  0.0 ;
        }
    }

    public Long findBookByStatus(String status) {
       Long v =  this.physicalBookRepo.findAllByStatus(status);
        return v!=null ?v :0;
    }

    public Double getTotalSalesForCurrentYear() {

        Double value = this.physicalBookRepo.findTotalSalesForCurrentYear() ;
        if (value != null){
            return  value.doubleValue() * 5 ;
        }
        else {
            return  0.0 ;
        }
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
            BookEntity book = entity.getBook() ;
            if (book == null){
                continue;
            }
            CategoryEntity category = entity.getBook().getCategory();

            if (category != null){
                dto.setBookPath(book.getBookPath().replaceAll("/var/www/html" , "http://kid-pix.com"));
                dto.setCreatationDate(entity.getRequestDate());
                dto.setCatId(category.getCatID());
                dto.setBookId(entity.getPhysicalBookId());
                dto.setCatName(category.getCatName());
                dto.setCoverPage(book.getCoverPage());
                dto.setKidName(book.getKidName());
                dto.setRequestedDate(entity.getRequestDate());
                dto.setStatus(entity.getStatus());
                dto.setNumCopies(entity.getNumCopies());
                bookCatDTOS.add(dto);
            }

        }
        return bookCatDTOS;
    }

    public String cancelBook(Long bookId) {
        this.physicalBookRepo.delete(this.physicalBookRepo.findById(bookId).get());
        return "Cancelled Successfully " ;
    }
}
