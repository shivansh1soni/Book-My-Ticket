package com.book_my_show.Book.My.Show.repository;

import com.book_my_show.Book.My.Show.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplicationUser_Repo extends JpaRepository<ApplicationUser, UUID> {
    public ApplicationUser findByEmail(String email);
}
