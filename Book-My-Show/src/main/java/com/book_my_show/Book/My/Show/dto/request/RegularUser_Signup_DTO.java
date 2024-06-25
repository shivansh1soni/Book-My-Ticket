package com.book_my_show.Book.My.Show.dto.request;
import com.book_my_show.Book.My.Show.enums.UserType;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegularUser_Signup_DTO {
    //dto is for data transfer object : basically a design pattern
    String name;
    String email;
    long phoneNumber;
    String password;
    UserType type; //users for this application are of 3 types regular users, movie owner, hallowners
    int age;
}
