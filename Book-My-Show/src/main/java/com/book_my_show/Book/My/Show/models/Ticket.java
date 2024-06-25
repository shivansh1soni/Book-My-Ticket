package com.book_my_show.Book.My.Show.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Ticket {
    @Id //to mark this column as prim key
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    UUID id;

    @ManyToOne
    @JsonIgnore //to ignore this field in postman output otherwise error will occur if won;t use json ignore
    ApplicationUser user;

    @ManyToOne
    @JsonIgnore
    Movie movie;

    @ManyToOne //as multiple tickets are there for one hall
    @JsonIgnore
    Hall hall;

    @ManyToOne //many people tickets can have one same show
    @JsonIgnore
    Show_ent show;
}

