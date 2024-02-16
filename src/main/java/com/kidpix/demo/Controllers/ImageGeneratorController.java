package com.kidpix.demo.Controllers;


import com.kidpix.demo.Model.DTO.FinalizeBookDTO;
import com.kidpix.demo.Model.DTO.ImageGeneratorDTO;
import com.kidpix.demo.Model.Service.ImageGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/api/gen")
public class ImageGeneratorController {

    @Autowired
    private ImageGeneratorService imageGeneratorService;


    @PostMapping
    public ResponseEntity<?> generateImage (@RequestBody ImageGeneratorDTO imageGeneratorDTO) throws IOException {
        imageGeneratorDTO.setKidName( URLDecoder.decode(imageGeneratorDTO.getKidName(), StandardCharsets.UTF_8));

        System.out.println("List Story : " + imageGeneratorDTO.getStoryList());

        String s =  this.imageGeneratorService.generateImage(imageGeneratorDTO).replaceAll("/var/www/html" ,"http://kid-pix.com" );

        if (s.contains("error")){
            return ResponseEntity.status(406).body(s);
        }

        return  ResponseEntity.ok(s);
    }

}