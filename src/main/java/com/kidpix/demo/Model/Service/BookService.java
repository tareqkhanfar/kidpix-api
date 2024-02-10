package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.BookCatDTO;
import com.kidpix.demo.Model.DTO.BookDTO;
import com.kidpix.demo.Model.DTO.FinalizeBookDTO;
import com.kidpix.demo.Model.DTO.ResponseDigitalBooksDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Repositories.BookRepository;
import com.kidpix.demo.Model.Repositories.SceneRepo;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
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
    private UserService userService ;

    @Autowired
    private BookRepository bookRepository ;

    @Autowired
    private CategoryService categoryService ;


    public BookEntity createBook(BookEntity bookEntity) {
        bookEntity.setStatus("Pending");
        bookEntity.setCreatedBook(new Date());
      return this.bookRepository.save(bookEntity);

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

    public List<BookCatDTO> getAllBooksForUser(String email) {
       Long id =  this.userService.getUserInfoByEmail(email).getId();
       List<BookEntity> bookEntities =  this.bookRepository.getAllBooksForUser(id) ;
       List<BookCatDTO>bookCatDTOS = new LinkedList<>( );

       for (BookEntity entity : bookEntities) {
           BookCatDTO dto = new BookCatDTO()  ;
           dto.setBookPath(entity.getBookPath().replaceAll("/var/www/html" , "http://kid-pix.com"));
           dto.setCreatationDate(entity.getCreatedBook());
           dto.setCatId(entity.getCategory().getCatID());
           dto.setBookId(entity.getBook_id());
           dto.setCatName(entity.getCategory().getCatName());
           dto.setThemePath(entity.getCategory().getThemeImagePath());
           bookCatDTOS.add(dto);
        }
        return bookCatDTOS;
    }

    public FinalizeBookDTO getBookData(Long bookId) {
        FinalizeBookDTO  dto = new FinalizeBookDTO() ;
      BookEntity bookEntity =   this.bookRepository.findById(bookId).get() ;

      dto.setCreationDate(bookEntity.getCreatedBook());
      dto.setThemeId(bookEntity.getCategory().getCatID());
      dto.setCoverPage(bookEntity.getCoverPage());
      dto.setBookPath(bookEntity.getBookPath().replaceAll("/var/www/html" ,"http://kid-pix.com"));
      dto.setThemeName(bookEntity.getCategory().getCatName());
      dto.setThemeDescription(bookEntity.getCategory().getDescription());
      return dto ;
    }

    public String cancelBook(Long bookId) {
        this.bookRepository.deleteById(bookId);
        return "deleted Successfully";
    }
}
