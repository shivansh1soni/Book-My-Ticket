package com.book_my_show.Book.My.Show.service;


import com.book_my_show.Book.My.Show.dto.request.MovieOwner_SignUp_DTO;
import com.book_my_show.Book.My.Show.models.ApplicationUser;
import com.book_my_show.Book.My.Show.models.Movie;
import com.book_my_show.Book.My.Show.models.Ticket;
import com.book_my_show.Book.My.Show.repository.ApplicationUser_Repo;
import com.book_my_show.Book.My.Show.repository.Movie_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    @Autowired
    Movie_Repo movieRepo;

    @Autowired
    ApplicationUser_Repo applicationUser_repo;

    public ApplicationUser signUp(MovieOwner_SignUp_DTO movieOwner_signUp_dto) {
        ApplicationUser movieOwner = new ApplicationUser();
        movieOwner.setAge(movieOwner_signUp_dto.getCompanyAge());
        movieOwner.setType(movieOwner_signUp_dto.getType().toString());
        movieOwner.setName(movieOwner_signUp_dto.getName());
        movieOwner.setPhoneNumber(movieOwner_signUp_dto.getPhoneNumber());
        movieOwner.setEmail(movieOwner_signUp_dto.getEmail());
        movieOwner.setPassword(movieOwner_signUp_dto.getPassword());
        List<Movie> movieList = movieOwner_signUp_dto.getMovieList();

        applicationUser_repo.save(movieOwner);

        for (Movie movie : movieList) {
            movie.setOwner(movieOwner);
            movieRepo.save(movie);
        }

        return movieOwner;
    }

    public Movie getMovieById(UUID id) {
        return movieRepo.findById(id).orElse(null);
    }

    public int getTotalTicketCount(Movie movie) {
        return movie.getTickets().size();
    }

    public int boxOfficeCollection(Movie movie) {
        int totalIncome = 0;
        for (Ticket tickets : movie.getTickets()) {
            totalIncome += tickets.getShow().getTicketPrice();
        }
        return totalIncome;
    }
}
