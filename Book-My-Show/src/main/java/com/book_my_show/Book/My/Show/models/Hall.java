package com.book_my_show.Book.My.Show.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    String address;

    @ManyToOne
    ApplicationUser owner; //many halls are handled by one owner /owner may be inox, pvr or local owner
    //let's say pvr is owner who is responsible for updating pvr halls, inox is a owner is updating inox halls, so pvr user can't do change the halls screen etc properties which is owned by inox

    @OneToMany(mappedBy = "hall")
    List<Screen> screens; //because particular hall have multiple screen

    @OneToMany(mappedBy = "hall") //from shows pov many shows can be listed to one hall, from multiple shows one show
    List<Show_ent> shows;

    @OneToMany(mappedBy = "hall")
    List<Ticket> tickets;
}

