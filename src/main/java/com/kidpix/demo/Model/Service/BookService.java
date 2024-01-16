package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.BookDTO;
import com.kidpix.demo.Model.DTO.ResponseDigitalBooksDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Repositories.BookRepository;
import com.kidpix.demo.Model.Repositories.SceneRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository ;

    @Autowired
    private CategoryService categoryService ;


    public BookEntity createBook(BookEntity bookEntity) {
        bookEntity.setStatus("Pending");
        bookEntity.setCreatedBook(new Date());
      return bookRepository.save(bookEntity);

    }


    public void addSceneToBook(Long bookId, Long catId) {
        BookEntity bookEntity = this.bookRepository.findById(bookId).get();
        bookEntity.setCategory(categoryService.findCatById(catId));
        this.bookRepository.save(bookEntity);


    }

    public BookEntity findBookById(Long bookId) {
        return this.bookRepository.findById(bookId).get() ;
    }

    public BookEntity getBookById(Long bookId) {
        return this.bookRepository.findById(bookId).get();
    }



    public Long totalBookInCurrentMonth() {
        return this.bookRepository.findTotalSalesForCurrentMonth() ;
    }

    public Long totalBookInCurrentYear() {
        return this.bookRepository.findTotalSalesForCurrentYear() ;
    }

    public Long totalClientPurchesDigitalBook() {
       return this.bookRepository.totalUsersPurchesBook();
    }

    public List<ResponseDigitalBooksDTO>getAllBooks() {
        List<ResponseDigitalBooksDTO> responseDigitalBooksDTOS = new LinkedList<>() ;
        List<BookEntity> bookEntities = this.bookRepository.findAll();
        for (BookEntity bookEntity : bookEntities) {
            ResponseDigitalBooksDTO digitalBooksDTO = new ResponseDigitalBooksDTO() ;
           digitalBooksDTO.setCreatedBook(bookEntity.getCreatedBook());
           digitalBooksDTO.setUsername(bookEntity.getUser().getUserName());
           digitalBooksDTO.setEmail(bookEntity.getUser().getEmail());

           System.out.println(bookEntity.getBook_id());
           digitalBooksDTO.setThemeName(bookEntity.getCategory().getCatName());
            responseDigitalBooksDTOS.add(digitalBooksDTO);
        }
        return responseDigitalBooksDTOS ;
    }

    public void getAllBooksForUser(String email) {
        // this.bookRepository.getAllBooksForUser(email) ;
    }
}
