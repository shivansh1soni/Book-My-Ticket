package com.book_my_show.Book.My.Show.controller;


import com.book_my_show.Book.My.Show.dto.request.RegularUser_Signup_DTO;
import com.book_my_show.Book.My.Show.models.ApplicationUser;
import com.book_my_show.Book.My.Show.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user") //defining base controller
public class RegularUserController {

    @Autowired
    RegularUserService regularUserService;

    @PostMapping("/signUp")
    public ResponseEntity SignUp(@RequestBody RegularUser_Signup_DTO regularUserSignupDto){
        ApplicationUser user = regularUserService.SignUp(regularUserSignupDto);
        return new ResponseEntity(user, HttpStatus.CREATED); //once user got created it will show http status code for created
    }





}
