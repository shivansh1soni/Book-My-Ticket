package com.book_my_show.Book.My.Show.dto.request;


import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddShowDTO {
    int hour; //we are making this for current day not for future days
    int minutes;
    int ticketPrice;

    UUID movieid;
    UUID hallid;

}
