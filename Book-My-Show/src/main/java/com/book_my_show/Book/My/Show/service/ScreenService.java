package com.book_my_show.Book.My.Show.service;


import com.book_my_show.Book.My.Show.models.Screen;
import com.book_my_show.Book.My.Show.repository.Screen_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScreenService {

    @Autowired
    Screen_Repo screenRepo;

    public void registerScreen(Screen screen){
        screenRepo.save(screen);
    }

    public void bookScreen(UUID id){
        screenRepo.bookScreen(id);
    }
}
