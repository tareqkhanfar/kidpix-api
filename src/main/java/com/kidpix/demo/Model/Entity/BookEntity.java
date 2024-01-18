package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "book_tbl")
@Data
public class BookEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "book_id")
    private Long book_id;


    @Column(name = "kid_name")
    private String kidName;


    @Column(name = "kid_photo" , length = 2000)
    private String kid_photo ;

    @Column(name = "gender")
    private String gender ;

    @Column(name = "age")
    private Integer age ;

    @Column(name = "additional_notes" , length = 2000)
    private String notes ;

    @Column(name = "book_path" , length = 2000)
    private String bookPath ;


    @Column(name = "status")
    private String status ;



    @Column(name = "cover_page")
    private String coverPage ;



    @Column(name = "created_book")
    private Date createdBook;


    @ManyToOne
    @JoinColumn(name = "cat_id")
    private CategoryEntity category ;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user ;


    @OneToMany(mappedBy = "book")
    private List<StoryTextEntity> storyTextEntities ;

    @OneToMany(mappedBy = "book")
    private List<PhysicalBookEntity> physicalBookEntities ;

}
