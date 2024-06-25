package com.book_my_show.Book.My.Show.service;


import com.book_my_show.Book.My.Show.models.Show_ent;
import com.book_my_show.Book.My.Show.repository.Show_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShowService {
    @Autowired
    Show_Repo showRepo;

    public void createShow(Show_ent show){
        showRepo.save(show);
    }

    public List<Show_ent> getShowsBymovieId(UUID movieId){
        return showRepo.getShowBymovieId(movieId);
    }

    public List<Show_ent> getShowByHallId(UUID hallId){
        return showRepo.getShowByHallId(hallId);
    }

    public List<Show_ent> getShowByhallIdANDmovieId(UUID id1, UUID id2){
        return showRepo.getShowBymovieIdANDhallId(id1, id2);
    }

    public Show_ent getShowByshowId(UUID showId){
        return showRepo.findById(showId).orElse(null);
    }


    /*
        Decrease available ticket count for a particular show
    */
    public void updateAvailableTicketCount(Show_ent show){
        int updatedAvailableTickets = show.getAvailableTickets() - 1;
        UUID showId = show.getId();
        showRepo.updateAvailableTicket(showId, updatedAvailableTickets);
    }
}
