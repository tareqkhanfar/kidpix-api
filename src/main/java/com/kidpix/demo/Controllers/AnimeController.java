package com.kidpix.demo.Controllers;



import com.kidpix.demo.Model.DTO.RequestApi;
import com.kidpix.demo.Model.Service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/animegan")
public class AnimeController {



    @Autowired
    private AnimeService animeService;
    @PostMapping
    public ResponseEntity<?> convertToAnime (@RequestBody RequestApi requestApi) throws IOException {
        System.out.println("test /: "+requestApi.getKidName());
        return  ResponseEntity.ok(this.animeService.convertToAnime(requestApi));
    }
}
