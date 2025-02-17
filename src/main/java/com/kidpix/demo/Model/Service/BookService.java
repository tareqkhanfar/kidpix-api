package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.DTO.BookCatDTO;
import com.kidpix.demo.Model.DTO.BookDTO;
import com.kidpix.demo.Model.DTO.FinalizeBookDTO;
import com.kidpix.demo.Model.DTO.ResponseDigitalBooksDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.CategoryEntity;
import com.kidpix.demo.Model.Entity.PhysicalBookEntity;
import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Repositories.BookRepository;
import com.kidpix.demo.Model.Repositories.SceneRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Autowired
    private PhysicalBookService physicalBookService ;

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
           if (bookEntity.getBookPath().equalsIgnoreCase("na")){
               continue;
           }
           System.out.println(bookEntity.getBook_id());
            CategoryEntity category = bookEntity.getCategory() ;
            if (category != null) {
                digitalBooksDTO.setThemeName(bookEntity.getCategory().getCatName());
                responseDigitalBooksDTOS.add(digitalBooksDTO);
            }

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
           if (dto.getBookPath().equalsIgnoreCase("na")){
               cancelBook(entity.getBook_id());
               continue;
           }
           dto.setCreatationDate(entity.getCreatedBook());
           dto.setBookId(entity.getBook_id());
           dto.setThemePath(entity.getCoverPage());
           dto.setKidName(entity.getKidName());
           CategoryEntity category = entity.getCategory() ;
           if (category != null) {
               dto.setCatId(entity.getCategory().getCatID());
               dto.setCatName(entity.getCategory().getCatName());
               bookCatDTOS.add(dto);
           }

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
        BookEntity bookEntity = this.bookRepository.findById(bookId).get();
     for  (PhysicalBookEntity book : bookEntity.getPhysicalBookEntities() ){
             if (book.getStatus().equalsIgnoreCase("Pending") ||book.getStatus().equalsIgnoreCase("Shipped") ){
                 throw new IllegalArgumentException("Please Cancel the Physical Books for this digital book ") ;
             }
             else {
                 this.physicalBookService.cancelBook(book.getPhysicalBookId()) ;
             }
     }
 String bookPath = bookEntity.getBookPath() ;
 String coverPage = "/var/www/html"+bookEntity.getCoverPage();
 String kidImage = "/var/www/html"+bookEntity.getKid_photo();
        deleteFile(bookPath);
        deleteFile(coverPage);
        deleteFile(kidImage);
        this.bookRepository.deleteById(bookId);
        return "deleted Successfully";
    }
    private static void deleteFile(String filePath) {
        System.out.println("Deleted Path : " + filePath);
        try {
            Path path = Paths.get(filePath);
            boolean deleted = Files.deleteIfExists(path);

            if (deleted) {
                System.out.println("File deleted successfully: " + filePath);
            } else {
                System.out.println("File not found, so it's not deleted: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while attempting to delete the file: " + filePath);
            e.printStackTrace();
        }
    }
}
