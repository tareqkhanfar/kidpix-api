package com.kidpix.demo.Model.Service;


import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Repositories.SceneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceneService {

    @Autowired
    private SceneRepo sceneRepo ;


    public SceneEntity saveScene(SceneEntity sceneEntity) {

       return sceneRepo.save(sceneEntity);
     }

    public List<SceneEntity> getAllScenes() {
      return   sceneRepo.findAll();
    }

    public List<SceneEntity> getScenesByCategory(Byte category) {

      return   sceneRepo.findAllByCategory(category) ;
    }

    public SceneEntity getSceneByPageNumber(byte category , byte pageNumber) {
        return   sceneRepo.findAllByCategoryAndPageNumber(category , pageNumber) ;

    }
}
