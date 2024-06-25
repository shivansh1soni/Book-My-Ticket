package com.book_my_show.Book.My.Show.dto.request;

import com.book_my_show.Book.My.Show.enums.UserType;
import com.book_my_show.Book.My.Show.models.Movie;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieOwner_SignUp_DTO {
    String name;
    String email;
    long phoneNumber;
    String password;
    UserType type; //users for this application are of 3 types regular users, movie owner, hallowners
    List<Movie> movieList; //particular movie owner going to provide list of movies
    int companyAge;
}
