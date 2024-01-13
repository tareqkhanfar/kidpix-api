package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Repositories.BookRepository;
import com.kidpix.demo.Model.Repositories.SceneRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository ;

    @Autowired
    private CategoryService categoryService ;


    public BookEntity createBook(BookEntity bookEntity) {
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
}
