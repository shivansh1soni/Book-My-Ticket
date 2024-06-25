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
public class ApplicationUser {
    //in hibernate user class is already present so we made application user to prevent error

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String name;
    @Column(unique = true)
    String email;
    @Column(unique = true)
    long phoneNumber;
    String password;
    String type; //users for this application are of 3 types regular users, movie owner, hallowners
    int age;

    @OneToMany(mappedBy = "user")//to preventing create same id field which is alreayd get created in user table
    List<Ticket> tickets;
}


