package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category_tbl")
@Data
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "cat_id")
    private Long catID;

    @Column(name = "cat_name")
    private String catName;

    @Column(name = "description")
    private String description ;

    @Column(name = "theme_image_path" , length = 2000)
    private String themeImagePath ;

    @Column(name = "theme_example" , length = 2000)
    private String themeExample ;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<SceneEntity > sceneEntityList ;


    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<BookEntity > bookEntities ;


    @Column(name =  "scribus_path")
   private String scribus_path ;

    @Column(name =  "theme_dir_path")
    private String ThemeDirPath ;

}
