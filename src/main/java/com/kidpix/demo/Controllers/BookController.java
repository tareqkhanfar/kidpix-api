package com.kidpix.demo.Controllers;

import com.kidpix.demo.Model.DTO.BookDTO;
import com.kidpix.demo.Model.DTO.CategoryAssignmentDTO;
import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Service.BookService;
import com.kidpix.demo.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/book")
@RestController
public class BookController {


    @Autowired
    private BookService bookService ;

    @Autowired
    private UserService userService ;


    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookEntity bookEntity = convertToEntity(bookDTO);
        BookEntity savedBook = bookService.createBook(bookEntity);
        return ResponseEntity.ok(convertToDTO(savedBook));
    }

    @PostMapping("/addCategoryToBook")
    public ResponseEntity<String> addSceneToBook(@RequestBody CategoryAssignmentDTO categoryAssignmentDTO) {
        System.out.println("book id "+categoryAssignmentDTO.getBookId());

        bookService.addSceneToBook(categoryAssignmentDTO.getBookId(), categoryAssignmentDTO.getCatId());
        return ResponseEntity.ok("https://dagrs.berkeley.edu/sites/default/files/2020-01/sample.pdf");
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
        return bookDTO;
    }


}
