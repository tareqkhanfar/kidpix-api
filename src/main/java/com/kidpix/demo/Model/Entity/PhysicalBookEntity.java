package com.kidpix.demo.Model.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "physical_book_tbl")
@Data
public class PhysicalBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "physical_book_id")
    private Long PhysicalBookId ;

    @Column(name = "num_copies")
    private Integer numCopies;

    @Column(name = "request_date")
    private Date requestDate;

    @Column(name = "status")
    private String status;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private BookEntity book ;


    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity ;




}
