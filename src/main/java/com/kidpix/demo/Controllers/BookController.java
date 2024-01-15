package com.kidpix.demo.Controllers;

import com.kidpix.demo.Model.DTO.BookDTO;
import com.kidpix.demo.Model.DTO.CategoryAssignmentDTO;
import com.kidpix.demo.Model.DTO.ResponseDigitalBooksDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Service.BookService;
import com.kidpix.demo.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;


@RequestMapping("/api/book")
@RestController
public class BookController {


    @Autowired
    private BookService bookService ;

    @Autowired
    private UserService userService ;





    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        /*
        try {
           // String path =  DecodeImage (bookDTO.getKidPhoto() , bookDTO.getFileExtension() , bookDTO.getUserEmail()) ;
            String path =
            bookDTO.setKidPhoto(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         */

        BookEntity bookEntity = convertToEntity(bookDTO);


        BookEntity savedBook = bookService.createBook(bookEntity);
        return ResponseEntity.ok(convertToDTO(savedBook));
    }

    @GetMapping("/getAllDigitalBooks")
 public ResponseEntity<List<ResponseDigitalBooksDTO>> bookDTOResponseEntity() {
      return  ResponseEntity.ok(this.bookService.getAllBooks()) ;
    }
    private String DecodeImage(String bookPath, String fileExtension , String userEmail) throws IOException {
        BufferedImage image = null;
        byte[] imageByte;

            Base64.Decoder decoder = Base64.getDecoder();
            imageByte = decoder.decode(bookPath);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
           File outputfile = new File("KidImages/"+userEmail+""+ LocalDate.now()+"."+fileExtension);
           ImageIO.write(image, fileExtension, outputfile);

return outputfile.getAbsolutePath();
    }



    @PostMapping("/addCategoryToBook")
    public ResponseEntity<String> addSceneToBook(@RequestBody CategoryAssignmentDTO categoryAssignmentDTO) {
        System.out.println("book id "+categoryAssignmentDTO.getBookId());

        bookService.addSceneToBook(categoryAssignmentDTO.getBookId(), categoryAssignmentDTO.getCatId());
        return ResponseEntity.ok("https://dagrs.berkeley.edu/sites/default/files/2020-01/sample.pdf");
    }


    @GetMapping("/totalDigitalBooksCurrentMonth")
    public ResponseEntity<Long> totalDigitalBooks() {
        return ResponseEntity.ok(this.bookService.totalBookInCurrentMonth());
    }

    @GetMapping("/totalDigitalBooksCurrentYear")
    public ResponseEntity<Long> totalDigitalYear() {
        return ResponseEntity.ok(this.bookService.totalBookInCurrentYear());
    }
    @GetMapping("/totalClientPurchesDigitalBook")
    public ResponseEntity<Long> totalClientPurchesDigitalBook() {
        return ResponseEntity.ok(this.bookService.totalClientPurchesDigitalBook());
    }

    public BookEntity convertToEntity(BookDTO bookDTO) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBook_id(bookDTO.getBookId());
        bookEntity.setKidName(bookDTO.getKidName());
        bookEntity.setKid_photo(bookDTO.getKidPhoto());
        bookEntity.setGender(bookDTO.getGender());
        bookEntity.setNotes(bookDTO.getNotes());
        bookEntity.setBookPath(bookDTO.getBookPath());
        bookEntity.setUser(userService.getUserInfoByEmail(bookDTO.getUserEmail()));
        bookEntity.setAge(bookDTO.getAge());
        bookEntity.setStatus(bookDTO.getStatus());
        bookEntity.setCreatedBook(bookDTO.getCreatedBook());
        // Map other fields as necessary
        return bookEntity;
    }

    public BookDTO convertToDTO(BookEntity bookEntity) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(bookEntity.getBook_id());
        bookDTO.setKidName(bookEntity.getKidName());
        bookDTO.setKidPhoto(bookEntity.getKid_photo());
        bookDTO.setGender(bookEntity.getGender());
        bookDTO.setNotes(bookEntity.getNotes());
        bookDTO.setBookPath(bookEntity.getBookPath());
        bookDTO.setUserEmail(bookEntity.getUser().getEmail());
        bookDTO.setAge(bookEntity.getAge());
        bookDTO.setStatus(bookEntity.getStatus());
        bookDTO.setCreatedBook(bookEntity.getCreatedBook());
        return bookDTO;
    }


}
