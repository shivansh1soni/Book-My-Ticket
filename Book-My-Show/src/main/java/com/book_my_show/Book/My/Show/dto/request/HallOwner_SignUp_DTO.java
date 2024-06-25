package com.book_my_show.Book.My.Show.dto.request;


import com.book_my_show.Book.My.Show.enums.UserType;
import com.book_my_show.Book.My.Show.models.Hall;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HallOwner_SignUp_DTO {
    String name;
    String email;
    long phoneNumber;
    String password;
    UserType type; //// users for this application are of 3 types regular users, movie owner, hallowners
    List<Hall> halls;
    int companyAge; //eg pvr
}
