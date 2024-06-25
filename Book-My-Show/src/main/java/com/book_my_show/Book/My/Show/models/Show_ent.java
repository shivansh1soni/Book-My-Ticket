package com.book_my_show.Book.My.Show.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Show_ent {

    //show is a keyword in mysql so we used show_ent

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @JsonIgnore //to remove the error in postman while hitting for show
    @ManyToOne
    Hall hall;

    @JsonIgnore
    @ManyToOne
    Movie movie;

    @JsonIgnore
    @ManyToOne
    Screen screen;

    int availableTickets;
    Date startTime;
    Date endTime;
    int ticketPrice;

    @OneToMany(mappedBy = "show")
    List<Ticket> tickets;
}
