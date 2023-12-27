package com.kidpix.demo.Model.Service;

import com.kidpix.demo.Model.Entity.BookEntity;
import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Repositories.BookRepository;
import com.kidpix.demo.Model.Repositories.SceneRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository ;

    @Autowired
    private SceneRepo sceneRepository ;
    public BookEntity createBook(BookEntity bookEntity) {
      return   bookRepository.save(bookEntity);

    }

    @Transactional
    public void addSceneToBook(Long bookId, Long sceneId) {
        BookEntity book = bookRepository.findById(bookId).orElseThrow(/* NotFoundException */);
        SceneEntity scene = sceneRepository.findById(sceneId).orElseThrow(/* NotFoundException */);
        book.getScenes().add(scene);
        bookRepository.saveAndFlush(book);

    }

}
