package com.book_my_show.Book.My.Show.controller;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.book_my_show.Book.My.Show.models.Show_ent;
import com.book_my_show.Book.My.Show.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    ShowService showService;


//    @Parameters({
//            @Parameter(name = "movieId", description = "It accepts only UUID", required = true),
//            @Parameter(name = "hallId", description = "It accepts only UUID")
//    })



    @GetMapping("/search")
    public ResponseEntity searchShowByMovieId(@RequestParam(required = false, defaultValue = "xyz") UUID movieId, @RequestParam(required = false,defaultValue = "zyx") UUID hallId){
        //required = false means while post man call weather you fill that field or keep it empty it still work

        if(movieId != null && hallId != null ){
            //search for both movie id and hall id
            List<Show_ent> shows = showService.getShowByhallIdANDmovieId(movieId, hallId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }
        else if(movieId == null && hallId !=null ){
            //if user want to search by only hall id
            List<Show_ent> shows = showService.getShowByHallId(hallId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }

        else if(movieId != null && hallId == null){
            //want to search by movie id
            List<Show_ent> shows = showService.getShowsBymovieId(movieId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }else{
            return new ResponseEntity("Please pass atleast one parameter", HttpStatus.NOT_ACCEPTABLE);
        }

    }

}