package com.book_my_show.Book.My.Show.repository;

import com.book_my_show.Book.My.Show.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Movie_Repo extends JpaRepository<Movie, UUID> {

}
