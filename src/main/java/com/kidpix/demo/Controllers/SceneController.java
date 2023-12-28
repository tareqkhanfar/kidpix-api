package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.SceneDTO;
import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Repositories.CategoryRepo;
import com.kidpix.demo.Model.Service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/scene")
public class SceneController {


    @Autowired
    private SceneService sceneService ;

    @Autowired
    private CategoryRepo categoryRepo ;



    @PostMapping
    public ResponseEntity<SceneDTO> addScene(@RequestBody SceneDTO sceneDTO) {
        SceneEntity sceneEntity = convertDTOToEntity(sceneDTO);
        SceneEntity savedEntity = sceneService.saveScene(sceneEntity);
        SceneDTO savedDto = convertEntityToDTO(savedEntity);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SceneDTO>> getAllScenes() {
        List<SceneEntity> scenes = sceneService.getAllScenes();
        List<SceneDTO> dtoList = scenes.stream()
                .map(SceneController::convertEntityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<SceneDTO>> getScenesByCategory(@PathVariable Long category) {
        List<SceneEntity> scenes = sceneService.getScenesByCategory(category);
        List<SceneDTO> dtoList = scenes.stream()
                .map(SceneController::convertEntityToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/{category}/page/{pageNumber}")
    public ResponseEntity<SceneDTO> getSceneByPageNumber(@PathVariable Long category , @PathVariable byte pageNumber) {
        SceneEntity scene = sceneService.getSceneByPageNumber(category , pageNumber);
        if (scene != null) {
            SceneDTO sceneDTO = SceneController.convertEntityToDTO(scene);
            return ResponseEntity.ok(sceneDTO);
        }
        return ResponseEntity.notFound().build();
    }




    public static SceneDTO convertEntityToDTO(SceneEntity sceneEntity) {
        SceneDTO sceneDTO = new SceneDTO();
        sceneDTO.setSceneId(sceneEntity.getScene_id());
        sceneDTO.setScenePath(sceneEntity.getScenePath());
        sceneDTO.setKeywords(sceneEntity.getKeywords());
        sceneDTO.setCategoryId(sceneEntity.getCategory().getCatID());
        sceneDTO.setPageNumber(sceneEntity.getPageNumber());
        return sceneDTO;
    }

    public  SceneEntity convertDTOToEntity(SceneDTO sceneDTO) {
        SceneEntity sceneEntity = new SceneEntity();
        sceneEntity.setScene_id(sceneDTO.getSceneId());
        sceneEntity.setScenePath(sceneDTO.getScenePath());
        sceneEntity.setKeywords(sceneDTO.getKeywords());
        sceneEntity.setCategory(categoryRepo.findById(sceneDTO.getCategoryId()).get());
        sceneEntity.setPageNumber(sceneDTO.getPageNumber());
        return sceneEntity;
    }



}
