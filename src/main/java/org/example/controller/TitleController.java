package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.dto.Title;
import org.example.service.TitleService;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.logging.Logger;

@RestController
@RequestMapping("title")
public class TitleController {
    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }
    @GetMapping("/{id}")
    public Title getTitle(@PathVariable("id") int id){
        return titleService.getTitle(id);
    }
    @GetMapping("all")
    public Collection<Title> getWithParam(@RequestParam (value = "startWithLikeX",required = false,defaultValue = "") String startWithLikeX ){
        System.out.println("@RequestParam  " + startWithLikeX);
        return titleService.getWithParam(startWithLikeX);
    }
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Title createTitle(@RequestBody Title example){
       return titleService.createTitle(example);
    }
}
