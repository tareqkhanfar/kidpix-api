package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Entity.StoryTextEntity;
import com.kidpix.demo.Model.Repositories.SceneRepo;
import com.kidpix.demo.Model.Repositories.StoryTextRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SceneService {

    @Autowired
    private SceneRepo sceneRepo ;

    @Autowired
    private StoryTextRepo storyTextRepo ;


    public SceneEntity saveScene(SceneEntity sceneEntity) {

       return sceneRepo.save(sceneEntity);
     }

    public List<SceneEntity> getAllScenes() {
      return   sceneRepo.findAll();
    }

    public List<SceneEntity> getScenesByCategory(Long category) {

          return this.sceneRepo.findByCategory_CatID(category) ;
    }

    public SceneEntity getSceneByPageNumber(Long category , byte pageNumber) {
        return null ;

    }


    public StoryTextEntity addTextToScene(StoryTextEntity storyTextEntity ) {
        return  this.storyTextRepo.save(storyTextEntity) ;
    }

    public SceneEntity findSceneById(Long sceneId) {
        return this.sceneRepo.findById(sceneId).get() ;
    }
}
