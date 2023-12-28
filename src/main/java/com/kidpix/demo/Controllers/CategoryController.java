package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.CategoryDTO;

import com.kidpix.demo.Model.Entity.CategoryEntity;
import com.kidpix.demo.Model.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/themes")
public class CategoryController {

    @Autowired
    private CategoryService themeService ;

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAllThemes() {
        List<CategoryDTO> themeDTOs = themeService.findAllThemes().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(themeDTOs);
    }

    @GetMapping("/getCategoryById/{catId}")
    public ResponseEntity<CategoryDTO> getCatById(@PathVariable Long catId) {
        return ResponseEntity.ok(convertToDTO(themeService.findCatById(catId)));
    }

    public CategoryEntity convertToEntity(CategoryDTO categoryDTO) {
        CategoryEntity catrgoryEntity = new CategoryEntity();
        catrgoryEntity.setCatID(categoryDTO.getCatId());
        catrgoryEntity.setCatName(categoryDTO.getCatName());
        catrgoryEntity.setDescription(categoryDTO.getDescription());
        catrgoryEntity.setThemeImagePath(categoryDTO.getCatPath());
        catrgoryEntity.setThemeExample(categoryDTO.getCatExample());
        // Note: The list of SceneEntity is not set here
        return catrgoryEntity;
    }

    public CategoryDTO convertToDTO(CategoryEntity catrgoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCatId(catrgoryEntity.getCatID());
        categoryDTO.setCatName(catrgoryEntity.getCatName());
        categoryDTO.setDescription(catrgoryEntity.getDescription());
        categoryDTO.setCatPath(catrgoryEntity.getThemeImagePath());
        categoryDTO.setCatExample(catrgoryEntity.getThemeExample());
        return categoryDTO;
    }




}
