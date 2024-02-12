package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.SceneDTO;
import com.kidpix.demo.Model.DTO.StoryTextDTO;
import com.kidpix.demo.Model.Entity.SceneEntity;
import com.kidpix.demo.Model.Entity.StoryTextEntity;
import com.kidpix.demo.Model.Repositories.CategoryRepo;
import com.kidpix.demo.Model.Repositories.StoryTextRepo;
import com.kidpix.demo.Model.Service.BookService;
import com.kidpix.demo.Model.Service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/scene")
public class SceneController {


    @Autowired
    private SceneService sceneService ;

    @Autowired
    private CategoryRepo categoryRepo ;

    @Autowired
    private BookService bookService ;





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
    @GetMapping("/category/{category}/{NAME}/{LANG}")
    public ResponseEntity<List<SceneDTO>> getScenesByCategory(@PathVariable("category") Long category , @PathVariable("NAME") String Name , @PathVariable("LANG") String lang) {

        Name =  URLDecoder.decode(Name, StandardCharsets.UTF_8);
        List<SceneEntity> scenes = sceneService.getScenesByCategory(category);
        for (SceneEntity  sceneEntity : scenes) {
            if (lang.equalsIgnoreCase("ar")){
                sceneEntity.setDefualtStoryText(sceneEntity.getStory_txt_ar().replaceAll("\\[NAME\\]", Name));
                sceneEntity.setDefualtStoryText(sceneEntity.getStory_txt_ar().replaceAll("\\[الاسم\\]", Name));


            }
            else {
                sceneEntity.setDefualtStoryText(sceneEntity.getDefualtStoryText().replaceAll("\\[NAME\\]", Name));
            }
        }
        List<SceneDTO> dtoList = scenes.stream()
                .map(SceneController::convertEntityToDTO)
                .collect(Collectors.toList());

        System.out.println(dtoList);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/getSceneById/{sceneId}")
    public ResponseEntity<SceneDTO> getSceneById(@PathVariable Long sceneId) {
        SceneEntity sceneEntity = this.sceneService.findSceneById(sceneId) ;
        SceneDTO dto = convertEntityToDTO(sceneEntity) ;
        return ResponseEntity.ok(dto);
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

@PostMapping("/addStoryToBook")
public ResponseEntity<StoryTextDTO> storyTextToBook (@RequestBody StoryTextDTO storyTextDTO) {
        return ResponseEntity.ok(convertEntityToDto(this.sceneService.addTextToScene(convertDtoToEntity(storyTextDTO))));
}


    public StoryTextEntity convertDtoToEntity(StoryTextDTO dto) {
        StoryTextEntity entity = new StoryTextEntity();
        entity.setStory_id(dto.getStoryId());
        entity.setBook(this.bookService.findBookById(dto.getBookId()));
        entity.setStoryText(dto.getStoryText());
        return entity;
    }
    public StoryTextDTO convertEntityToDto(StoryTextEntity entity) {
        StoryTextDTO dto = new StoryTextDTO();
        dto.setStoryId(entity.getStory_id());
        dto.setBookId(entity.getBook().getBook_id());
        dto.setStoryText(entity.getStoryText());
        return dto;
    }



    public static SceneDTO convertEntityToDTO(SceneEntity sceneEntity) {
        SceneDTO sceneDTO = new SceneDTO();
        sceneDTO.setSceneId(sceneEntity.getScene_id());
        sceneDTO.setScenePath(sceneEntity.getScenePath());
        sceneDTO.setKeywords(sceneEntity.getKeywords());
        sceneDTO.setCategoryId(sceneEntity.getCategory().getCatID());
        sceneDTO.setPageNumber(sceneEntity.getPageNumber());
        sceneDTO.setDefualtStoryText(sceneEntity.getDefualtStoryText());
        return sceneDTO;
    }

    public  SceneEntity convertDTOToEntity(SceneDTO sceneDTO) {
        SceneEntity sceneEntity = new SceneEntity();
        sceneEntity.setScene_id(sceneDTO.getSceneId());
        sceneEntity.setScenePath(sceneDTO.getScenePath());
        sceneEntity.setKeywords(sceneDTO.getKeywords());
        sceneEntity.setCategory(categoryRepo.findById(sceneDTO.getCategoryId()).get());
        sceneEntity.setPageNumber(sceneDTO.getPageNumber());
        sceneEntity.setDefualtStoryText(sceneDTO.getDefualtStoryText());
        sceneEntity.setStory_txt_ar(sceneDTO.getDefualtStoryText());
        return sceneEntity;
    }



}
