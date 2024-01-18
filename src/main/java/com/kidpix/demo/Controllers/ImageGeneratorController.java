package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.FinalizeBookDTO;
import com.kidpix.demo.Model.DTO.ImageGeneratorDTO;
import com.kidpix.demo.Model.Service.ImageGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/gen")
public class ImageGeneratorController {

    @Autowired
    private ImageGeneratorService imageGeneratorService;

    @PostMapping
    public ResponseEntity<Map<String,String>> generateImage (@RequestBody ImageGeneratorDTO imageGeneratorDTO){
        Map<String , String> map  = this.imageGeneratorService.generateImage(imageGeneratorDTO);
              map.put("finalPath" ,  map.get("finalPath").replaceAll("/var/www/html" , "http://206.81.27.175") );

        return  ResponseEntity.ok(map);
    }







}
