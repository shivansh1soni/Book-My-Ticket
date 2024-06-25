package com.book_my_show.Book.My.Show.service;

import com.book_my_show.Book.My.Show.exception.ResourceNotExistException;
import com.book_my_show.Book.My.Show.exception.UnAuthorizedException;
import com.book_my_show.Book.My.Show.exception.UserDoesNotExistException;
import com.book_my_show.Book.My.Show.models.*;
import com.book_my_show.Book.My.Show.repository.ApplicationUser_Repo;
import com.book_my_show.Book.My.Show.repository.Ticket_Repo;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TicketService {


    @Autowired
    Ticket_Repo ticketRepo;

    @Autowired
    ApplicationUser_Repo applicationUserRepo;

    @Autowired
    ShowService showService;

    @Autowired
    MovieService movieService;

    @Autowired
    HallService hallService;

    @Autowired
    MailService mailService;

    public Ticket buyTicket(String email, UUID showId) {
        //1. Get user by email id
        ApplicationUser user = applicationUserRepo.findByEmail(email);
        //check whether user exist in db or not
        if (user == null) {
            throw new UserDoesNotExistException(String.format("User with email id %s doesn't exist", email));
        }
        //check user has required access or not : user type movieowner and hallowner are not allowed to perform this action
        if (!user.getType().equals("RegulaUser")) {
            throw new UnAuthorizedException(String.format("User with type %s are now allowed to perform this action", user.getType()));
        }
        //validate show with whatever id we are getting in function parameter is existing in our system or not
        Show_ent show = showService.getShowByshowId(showId);
        if (show == null) {
            throw new ResourceNotExistException(String.format("show with id %s doesn't exist in our system", showId));
        }
        //we have to decrease ticket count for this particular show id as we are buying one ticket
        showService.updateAvailableTicketCount(show);
        Ticket ticket = new Ticket();
        ticket.setHall(show.getHall());
        ticket.setMovie(show.getMovie());
        ticket.setShow(show);
        ticket.setUser(user);

        ticketRepo.save(ticket); //to save in the database

        Movie movie = movieService.getMovieById(show.getMovie().getId());
        Hall hall = hallService.getHallById(show.getHall().getId());

        //send email once booked
        //send ticket details to user
        String userMessage = String.format("Hey %s,\n" +
                        "Congratulations!! your ticket has been booked successfully! Below are your ticket details: \n" +
                        "1. Movie Name - %s \n" +
                        "2. Hall Name - %s \n" +
                        "3. Hall Address - %s \n" +
                        "4. Date and Time - %s \n" +
                        "5. Ticket Price - %d \n" +
                        "\n Hope You will Enjoy your show. Bye:)" +
                        "Team BookMeraShow", user.getName(), movie.getName(), hall.getName(), hall.getAddress(), show.getStartTime().toString(),
                show.getTicketPrice());


        String mailsub = String.format("Congratulations %s !! Your ticket is booked", user.getName());


//        mailService.generateMail(user.getEmail(), mailsub, userMessage);


        //send box office collection to movie owner
        int total_tickets = movieService.getTotalTicketCount(movie);
        int total_income = movieService.boxOfficeCollection(movie);

        String movie_mes = String.format("Hii %s \n" +
                "Congratulations!! Your ticket got sold\n" +
                "Total Ticket sold: %d" +
                "Total Income: %d", movie.getOwner().getName(), total_tickets, total_income);

        String movieSubject = String.format("Congratulations !! %s One more ticket got sold", movie.getOwner().getName());

        mailService.generateMail(movie.getOwner().getEmail(), movieSubject, movie_mes);
        return ticket;
    }
}
