package com.book_my_show.Book.My.Show.controller;

import com.book_my_show.Book.My.Show.dto.request.AddScreenDTO;
import com.book_my_show.Book.My.Show.dto.request.AddShowDTO;
import com.book_my_show.Book.My.Show.dto.request.HallOwner_SignUp_DTO;

import com.book_my_show.Book.My.Show.dto.response.GeneralMessageDTO;
import com.book_my_show.Book.My.Show.exception.ResourceNotExistException;
import com.book_my_show.Book.My.Show.exception.UnAuthorizedException;
import com.book_my_show.Book.My.Show.exception.UserDoesNotExistException;
import com.book_my_show.Book.My.Show.models.ApplicationUser;
import com.book_my_show.Book.My.Show.models.Screen;
import com.book_my_show.Book.My.Show.models.Show_ent;
import com.book_my_show.Book.My.Show.service.HallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="Hall API", description = "This controller contains hall related details")
@RestController
@RequestMapping("/hall")
public class HallController {

    @Autowired
    HallService hallService;

    @PostMapping(value = "/signup_hall", consumes = {"*/*"})
    public ResponseEntity signUp(@RequestBody HallOwner_SignUp_DTO hallOwnerSignUpDto){
        ApplicationUser hallOwner = hallService.signUp(hallOwnerSignUpDto);
        return new ResponseEntity(hallOwner, HttpStatus.CREATED);
    }

    @Operation(
            summary = "This endpoint enables hall owners such that owners can add screens to their respective halls",
            tags = {"Learning :","About Post mapping"}
    )

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Screen got added successfully in the respective hall", content = {@Content(schema = @Schema(implementation = Screen.class), mediaType = "Application/json")}),
            @ApiResponse(responseCode = "404", description = "User does not exist", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")}),
            @ApiResponse(responseCode = "404", description = "Resource Not Found Exception", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")}),
            @ApiResponse(responseCode = "401", description = "User is not authorized to add screens into hall", content = {@Content(schema = @Schema(implementation = GeneralMessageDTO.class), mediaType = "Application/json")})
    })


    @PostMapping("/addscreen")
    public ResponseEntity addScreen(@RequestBody AddScreenDTO addScreenDTO, @RequestParam String email){
        try{
            hallService.addScreens(addScreenDTO, email);
        }catch(UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404
        }catch (UnAuthorizedException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED); //401
        }catch(ResourceNotExistException e){ //if a hall owner is wrongly want to access different hall or hall is not cumes under that user
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404 //rosroucces nto found
        }

        return new ResponseEntity(new GeneralMessageDTO("Screen added successfully"), HttpStatus.CREATED); //201
    }

    @PostMapping("/addShow")
    public ResponseEntity createShow(@RequestBody AddShowDTO addShowDTO, @RequestParam String email){
        try{
            Show_ent showEnt = hallService.createShow(addShowDTO, email);
            return new ResponseEntity(showEnt, HttpStatus.CREATED);
        }catch(UserDoesNotExistException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404
        }catch (UnAuthorizedException e){
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED); //401
        }catch(ResourceNotExistException e){ //if a hall owner is wrongly want to access different hall or hall is not cumes under that user
            return new ResponseEntity(new GeneralMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND); //404 //rosroucces nto found
        }

    }


}
