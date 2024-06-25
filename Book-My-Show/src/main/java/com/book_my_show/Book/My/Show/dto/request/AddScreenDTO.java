package com.book_my_show.Book.My.Show.dto.request;

import com.book_my_show.Book.My.Show.models.Screen;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddScreenDTO {
    List<Screen> screens;
    UUID hallId;

}
