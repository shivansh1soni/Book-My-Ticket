package com.book_my_show.Book.My.Show.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    String directorName;
    String actorName;
    String actressName;
    int imdbRating;
    double duration; //hours

    @OneToMany(mappedBy = "movie") //if you don't use mapped by it iwll create a 3rd table between between movie and ticket as movie id and ticket id, as ticket id is already used in ticket entity so we use movie object which is used in ticket entity
    List<Ticket> tickets; // i want to get box office collection so no of tickets is needed

    @ManyToOne //here many refers the class ie; movie and one refers to another entity
    ApplicationUser owner;     // many moevies is owned by one owner //mmovie owners are dharma production, redchillis
}
