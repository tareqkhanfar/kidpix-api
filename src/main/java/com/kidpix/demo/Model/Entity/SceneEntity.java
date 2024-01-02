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

    @Column(name = "scene_path", length = 2000)
    private String scenePath ;


    @Column(name = "keywords" , length = 2000)
    private String keywords ;

    @Column(name = "defualt_story_text" , length = 4000)
    private String defualtStoryText ;

    @Column(name = "story_text" , length = 4000)
    private String story_txt ;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private CategoryEntity category ;



}
