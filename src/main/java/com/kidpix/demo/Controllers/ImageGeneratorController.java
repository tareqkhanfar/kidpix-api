package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.ImageGeneratorDTO;
import com.kidpix.demo.Model.Service.ImageGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gen")
public class ImageGeneratorController {

    @Autowired
    private ImageGeneratorService imageGeneratorService;

    @PostMapping
    public ResponseEntity<?> generateImage (@RequestBody ImageGeneratorDTO imageGeneratorDTO){
        return  ResponseEntity.ok(this.imageGeneratorService.generateImage(imageGeneratorDTO));
    }




}
