package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.Data;

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


    @Column(name = "kid_photo")
    private String kid_photo ;

    @Column(name = "gender")
    private String gender ;

    @Column(name = "additional_notes")
    private String notes ;

    @Column(name = "book_path")
    private String bookPath ;


    @ManyToMany(cascade = CascadeType.ALL) // or an appropriate cascade type
    @JoinTable(
            name = "book_scene",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "scene_id")
    )
    private List<SceneEntity> scenes;







    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user ;


}
