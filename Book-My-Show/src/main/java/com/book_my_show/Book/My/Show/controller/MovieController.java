package com.book_my_show.Book.My.Show.controller;

import com.book_my_show.Book.My.Show.dto.request.MovieOwner_SignUp_DTO;
import com.book_my_show.Book.My.Show.models.ApplicationUser;
import com.book_my_show.Book.My.Show.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody MovieOwner_SignUp_DTO movieOwnerSignUpDto){
        ApplicationUser movieOwner = movieService.signUp(movieOwnerSignUpDto);
        return new ResponseEntity(movieOwner, HttpStatus.CREATED);
    }


}
