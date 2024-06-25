package com.book_my_show.Book.My.Show.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String screenName;
    @ManyToOne() //from screen point of view(because it's a screen entity) multiple screen present in one hall so many to one
    Hall hall; //we need hall id field in screen entity
    int screenCapacity;
    boolean status; //is the screen free now in the current moment or not
    String type; //2d screen or 3d screen

}


