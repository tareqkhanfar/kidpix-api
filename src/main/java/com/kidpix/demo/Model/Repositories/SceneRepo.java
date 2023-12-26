package com.kidpix.demo.Model.Repositories;

import com.kidpix.demo.Model.Entity.SceneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SceneRepo extends JpaRepository<SceneEntity , Long> {

    public List<SceneEntity> findAllByCategory(Byte cat) ;
    public SceneEntity findAllByCategoryAndPageNumber(Byte cat , Byte pageNumber) ;

}
