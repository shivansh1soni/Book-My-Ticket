package com.book_my_show.Book.My.Show.repository;

import com.book_my_show.Book.My.Show.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface Hall_Repo extends JpaRepository<Hall, UUID> {

}
