package com.book_my_show.Book.My.Show.repository;

import com.book_my_show.Book.My.Show.models.Show_ent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface Show_Repo extends JpaRepository<Show_ent, UUID> {

    @Query(value = "select * from show_ent where movie_id =:id", nativeQuery = true)
    public List<Show_ent> getShowBymovieId(UUID id);

    @Query(value = "select * from show_ent where hall_id =:id",nativeQuery = true)
    public List<Show_ent> getShowByHallId(UUID id);

    @Query(value = "select * from show_ent where movie_id =:id1 and hall_id=:id2",nativeQuery = true)
    public List<Show_ent> getShowBymovieIdANDhallId(UUID id1, UUID id2);


    @Transactional //if any modification in the database
    @Modifying
    @Query(value = "update show_ent set available_tickets =:updatedTicketCount where id =:id", nativeQuery = true)
    public void updateAvailableTicket(UUID id, int updatedTicketCount);

}
