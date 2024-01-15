package com.kidpix.demo.Controllers;

import com.kidpix.demo.Model.DTO.FinalizeStoryDTO;
import com.kidpix.demo.Model.Service.FinalizeStoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/finalizeStoryBook")
public class FinalizeStoryBook {

    @Autowired
    private FinalizeStoryService storyService;

    @PostMapping("/createStorybook")
    public String createStorybook(@RequestBody FinalizeStoryDTO request) {
        return storyService.createStorybook(request);
    }
}
