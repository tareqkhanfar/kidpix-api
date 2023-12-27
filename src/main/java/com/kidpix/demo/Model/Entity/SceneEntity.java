package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "scene_tbl")
@Data
public class SceneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "scene_id")
    private Long scene_id;


    @Column(name = "page_number")
    private Byte pageNumber ;

    @Column(name = "scene_path")
    private String scenePath ;


    @Column(name = "keywords")
    private String keywords ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private CatrgoryEntity category ;


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "book_scene",
            joinColumns = { @JoinColumn(name = "scene_id") },
            inverseJoinColumns = { @JoinColumn(name = "book_id") }
    )
    private List<BookEntity> books;

}
