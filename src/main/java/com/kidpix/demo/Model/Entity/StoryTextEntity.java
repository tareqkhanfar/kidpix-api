package com.kidpix.demo.Model.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "story_tbl")
@Data
public class StoryTextEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or another strategy like AUTO
    @Column(name = "story_id")
    private Long story_id;


    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity  book ;


    @Column(name = "story_text" , length = 4000)
    private String storyText ;


}
