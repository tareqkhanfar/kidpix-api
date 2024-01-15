package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.PhysicalBookDto;
import com.kidpix.demo.Model.DTO.PhysicalBookWithUserBook;
import com.kidpix.demo.Model.Entity.PhysicalBookEntity;
import com.kidpix.demo.Model.Service.BookService;
import com.kidpix.demo.Model.Service.PhysicalBookService;
import com.kidpix.demo.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/physicalbook")
@RestController
public class PhysicalBookController {


    @Autowired
    private UserService userService ;

    @Autowired
    private BookService bookService ;

    @Autowired
    private PhysicalBookService physicalBookService ;



    @PostMapping
    public PhysicalBookDto createPhysicalBook(@RequestBody PhysicalBookDto physicalBookDto) {
        PhysicalBookEntity entity = convertToEntity(physicalBookDto);
        entity = physicalBookService.createPhysicalBook(entity);
        return convertToDto(entity);
    }

    @GetMapping("/getPhysicalBooks")
    public List<PhysicalBookWithUserBook> physicalBookWithUserBooks() {
        return this.physicalBookService.getPhysicalBooks();
    }

    @GetMapping("/getTotalClientPurchesBook")
    public ResponseEntity<Long> getTotalClientPurchesBook() {
      return   ResponseEntity.ok(this.physicalBookService.getTotalClientPurchesBook());
    }
    @GetMapping("/getTotalSale")
    public ResponseEntity<Double> getTotalSale () {
     return   ResponseEntity.ok(this.physicalBookService.getTotalSales() );
    }

    @GetMapping("/getTotalSaleForCurrentMonth")
    public ResponseEntity<Double> getTotalSaleForCurrentMonth () {
        return   ResponseEntity.ok(this.physicalBookService.getTotalSalesForCurrentMonth() );
    }
    @GetMapping("/getTotalSaleForCurrentYear")
    public ResponseEntity<Double> getTotalSaleForCurrentYear () {
        return   ResponseEntity.ok(this.physicalBookService.getTotalSalesForCurrentYear() );
    }
    @GetMapping("/getCountByStatus/{status}")
    public ResponseEntity<Long> getStatusCount(@PathVariable("status") String  status){
        return ResponseEntity.ok( this.physicalBookService.findBookByStatus(status)) ;
    }

    private PhysicalBookDto convertToDto(PhysicalBookEntity entity) {
        PhysicalBookDto dto = new PhysicalBookDto();
        dto.setPhysicalBookId(entity.getPhysicalBookId());
        dto.setNumCopies(entity.getNumCopies());
        dto.setRequestDate(entity.getRequestDate());
        dto.setStatus(entity.getStatus());
        dto.setUserId(entity.getUser().getId()); // Assuming getUserId() exists
        dto.setBookId(entity.getBook().getBook_id()); // Assuming getBookId() exists
        return dto;
    }

    private PhysicalBookEntity convertToEntity(PhysicalBookDto dto) {
        PhysicalBookEntity entity = new PhysicalBookEntity();
        entity.setPhysicalBookId(dto.getPhysicalBookId());
        entity.setNumCopies(dto.getNumCopies());
        entity.setRequestDate(dto.getRequestDate());
        entity.setStatus(dto.getStatus());
        entity.setBook(this.bookService.getBookById(dto.getBookId()));
        entity.setUser(this.userService.getUserById(dto.getUserId()));
        return entity;
    }

}
