package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.*;
import com.kidpix.demo.Model.Entity.PhysicalBookEntity;
import com.kidpix.demo.Model.Service.AddressService;
import com.kidpix.demo.Model.Service.BookService;
import com.kidpix.demo.Model.Service.PhysicalBookService;
import com.kidpix.demo.Model.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/api/physicalbook")
@RestController
public class PhysicalBookController {


    @Autowired
    private UserService userService ;

    @Autowired
    private BookService bookService ;

    @Autowired
    private PhysicalBookService physicalBookService ;

    @Autowired
    private AddressService addressService ;




    @PostMapping
    public PhysicalBookDto createPhysicalBook(@RequestBody PhysicalBookDto physicalBookDto) {
        PhysicalBookEntity entity = convertToEntity(physicalBookDto);
        entity = physicalBookService.createPhysicalBook(entity);
        return convertToDto(entity);
    }


    @PostMapping("/changeStatus")
    public ResponseEntity<Map<String , Object>> changeStatus (@RequestBody Map<String, String> requestData){
       PhysicalBookEntity physicalBookEntity =  this.physicalBookService.changeStatus(Long.parseLong(requestData.get("bookId")) , requestData.get("status"));
       Map<String , Object> map = new HashMap<>() ;
       map.put("bookId" , physicalBookEntity.getPhysicalBookId());
       map.put("status" , physicalBookEntity.getStatus());
       return  ResponseEntity.ok(map);
    }

    /*
    @GetMapping("/getAllOrdersByUserEmail/{email}")
    public ResponseEntity<OrderDTO> getAllOrderByUserEmail(@PathVariable("email") String email){
      return ResponseEntity.ok(this.userService.getUserInfoByEmail(email).getPhysicalBookEntities().stream().collect(Collectors.toList())) ;
    }

     */

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
    @GetMapping("/getAllBooksForUser/{email}")
    public ResponseEntity<List<PhysicalBookForUser>>getAllBooksForUser(@PathVariable("email") String email) {
        return ResponseEntity.ok(this.physicalBookService.getAllBooksForUser(email));
    }

    @GetMapping("/cancel_book/{book_id}")
    public ResponseEntity<String> cancelPage(@PathVariable("book_id") Long bookId){
        return ResponseEntity.ok(this.physicalBookService.cancelBook(bookId));
    }
    private PhysicalBookDto convertToDto(PhysicalBookEntity entity) {
        PhysicalBookDto dto = new PhysicalBookDto();
      //  dto.setPhysicalBookId(entity.getPhysicalBookId());
        dto.setNumCopies(entity.getNumCopies());
      ///  dto.setRequestDate(entity.getRequestDate());
     //   dto.setStatus(entity.getStatus());
     //   dto.setUserId(entity.getUser().getId()); // Assuming getUserId() exists
        dto.setBookId(entity.getBook().getBook_id()); // Assuming getBookId() exists
        return dto;
    }

    private PhysicalBookEntity convertToEntity(PhysicalBookDto dto) {
        PhysicalBookEntity entity = new PhysicalBookEntity();
        entity.setNumCopies(dto.getNumCopies());
        entity.setBook(this.bookService.getBookById(dto.getBookId()));
        entity.setUser(this.bookService.getBookById(dto.getBookId()).getUser());
        entity.setAddressEntity(this.addressService.getAddressById(dto.getAddressId()));
        return entity;
    }

}
