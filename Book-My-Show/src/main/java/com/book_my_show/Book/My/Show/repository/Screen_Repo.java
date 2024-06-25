package com.book_my_show.Book.My.Show.repository;

import com.book_my_show.Book.My.Show.models.Screen;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface Screen_Repo extends JpaRepository<Screen, UUID> {

    @Transactional // we are doing some transaction in the data base
    @Modifying // as we are modifying something in the database

    @Query(value = "update screen set status=true where id =:screenId", nativeQuery = true)
    public void bookScreen(UUID screenId);
}
