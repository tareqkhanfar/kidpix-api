package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "category_tbl")
@Data
public class CatrgoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "cat_id")
    private Long catID;

    @Column(name = "cat_name")
    private String catName;

    @OneToMany(mappedBy = "category" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<SceneEntity > sceneEntityList ;

}
