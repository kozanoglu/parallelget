package com.kozanoglu.controller;

import com.kozanoglu.dto.UserPostResultDTO;
import com.kozanoglu.service.UserPostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    private UserPostService userPostService;

    public Controller(final UserPostService userPostService) {
        this.userPostService = userPostService;
    }

    @GetMapping(value = "/userposts/{userId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    UserPostResultDTO get( @PathVariable("userId") long id) {
        return userPostService.get(id);
    }
}
