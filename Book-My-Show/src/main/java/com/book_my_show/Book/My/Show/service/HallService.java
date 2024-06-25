package com.book_my_show.Book.My.Show.service;

import com.book_my_show.Book.My.Show.dto.request.AddScreenDTO;
import com.book_my_show.Book.My.Show.dto.request.AddShowDTO;
import com.book_my_show.Book.My.Show.dto.request.HallOwner_SignUp_DTO;
import com.book_my_show.Book.My.Show.exception.ResourceNotExistException;
import com.book_my_show.Book.My.Show.exception.UnAuthorizedException;
import com.book_my_show.Book.My.Show.exception.UserDoesNotExistException;
import com.book_my_show.Book.My.Show.models.*;
import com.book_my_show.Book.My.Show.repository.ApplicationUser_Repo;
import com.book_my_show.Book.My.Show.repository.Hall_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class HallService {

    @Autowired
    ApplicationUser_Repo applicationUser_repo;

    @Autowired
    RegularUserService regularUserService;

    @Autowired
    Hall_Repo hall_repo;

    @Autowired
    ScreenService screenService;

    @Autowired
    MovieService movieService;

    @Autowired
    ShowService showService;


    public ApplicationUser signUp(HallOwner_SignUp_DTO hallOwnerSignUpDto){

        ApplicationUser hallowner = new ApplicationUser();
        hallowner.setName(hallOwnerSignUpDto.getName());
        hallowner.setEmail(hallOwnerSignUpDto.getEmail());
        hallowner.setPassword(hallOwnerSignUpDto.getPassword());
        hallowner.setType(hallOwnerSignUpDto.getType().toString());
        hallowner.setPhoneNumber(hallOwnerSignUpDto.getPhoneNumber());
        hallowner.setAge(hallOwnerSignUpDto.getCompanyAge());
        List<Hall> halls = hallOwnerSignUpDto.getHalls();
//        System.out.println(hallowner.getId());
        applicationUser_repo.save(hallowner);
//        System.out.println(hallowner.getId());
//        Logger.getLogger(hallowner.getName());
        for(Hall hall: halls){
            hall.setOwner(hallowner);
            hall_repo.save(hall);
        }

        return hallowner;
    }

    public Hall getHallById(UUID id){
        return hall_repo.findById(id).orElse(null);
    }


    public void addScreens(AddScreenDTO addScreenDTO, String email){
        List<Screen> screens = addScreenDTO.getScreens();
        UUID hallId = addScreenDTO.getHallId();
        ApplicationUser user = regularUserService.getUserByEmail(email);
        if(user==null){
            throw new UserDoesNotExistException("User with this email doesn't exist");
        }
        if(!user.getType().equals("HallOwner")){ //this is a eg of authorization you are valid user but you are not allowed to perform some action
            throw new UnAuthorizedException("This user is not allowed to perform this task");
        }
        //now check this hall owner owns this hall or not
        Hall hall = getHallById(hallId);
        if(hall == null){
            throw new ResourceNotExistException(String.format("Hall with id %s doesn't exist in system", hallId.toString())); //this hallid is the variable which is passing through postman
        }
        if(hall.getOwner().getId() != user.getId()){
            throw new UnAuthorizedException("User does not own this hall id");
        }


        //the user who reach here that means that's a valid user
        for(Screen screen: screens){
            screen.setHall(hall);
            screenService.registerScreen(screen);
        }

    }

    public Show_ent createShow(AddShowDTO addShowDTO, String email){ //email is hallOwner's email who is going to do this opration
        //validate email : present in application user repo or not
        ApplicationUser user = applicationUser_repo.findByEmail(email);
        if(user==null){
            throw new UserDoesNotExistException(String.format("User with id %s doesn't exist in system", email));
        }

        //validate type of hall owner
        if(!user.getType().equals("HallOwner")){
            throw new UnAuthorizedException(String.format("User with email id %s doesn't have required permission to peform this action", email));
        }

        //validate hall id is existing in db or not
        UUID hallId = addShowDTO.getHallid();
        Hall hall = getHallById(hallId);
        if(hall==null){
            throw new ResourceNotExistException(String.format("Hall with id %s doesnot exist in the system", hallId.toString()));
        }

        //validate user owns this hall or not
        if(hall.getOwner().getId() !=  user.getId()){
            throw new UnAuthorizedException(String.format("User with email id %s doesnot won hall with hallid %s",email, hallId.toString()));
        }

        //validate movie which we got from addshowDTO: is it existing in our system or not
        UUID movieId = addShowDTO.getMovieid(); //we will get it from user
        Movie movie = movieService.getMovieById(movieId);

        if(movie==null){
            throw new ResourceNotExistException(String.format("Movie with movie id %s doesnot exist in the system", movieId.toString()));
        }

        //get screens that are not occupied
        List<Screen> screens = new ArrayList<>();
        for(Screen screen: hall.getScreens()){
            if(screen.isStatus()==false){
                screens.add(screen);
//                screen.setStatus(true);
//                break;
            }
        }

        //if we got no screen is free
        if(screens.size()==0){
            throw new ResourceNotExistException(String.format("Hall with hall id %s doesn't have any allocated screens",hallId.toString()));
        }

        Screen screen = screens.get(0);

        //setting up all the properties for the show
        Show_ent showEnt = new Show_ent();
        showEnt.setHall(hall);
        showEnt.setMovie(movie);
        showEnt.setAvailableTickets(screen.getScreenCapacity());
        showEnt.setTicketPrice(addShowDTO.getTicketPrice());
        showEnt.setScreen(screen); //allocating screen to show

        Date startDateTime = new Date();
        startDateTime.setHours(addShowDTO.getHour());
        startDateTime.setMinutes(addShowDTO.getMinutes());

        Date endDateTime = new Date();
        int hour = (int)(addShowDTO.getHour() + movie.getDuration() )%24;
        endDateTime.setHours(hour);
        endDateTime.setMinutes(addShowDTO.getMinutes());

        showEnt.setStartTime(startDateTime);
        showEnt.setEndTime(endDateTime);

        //mark status of screen as true, such that no other show can book that
        screenService.bookScreen(screen.getId());
        showService.createShow(showEnt);

        return showEnt;
    }
}
