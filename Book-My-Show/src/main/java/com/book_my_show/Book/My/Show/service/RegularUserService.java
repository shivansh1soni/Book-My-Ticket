package com.book_my_show.Book.My.Show.service;

import com.book_my_show.Book.My.Show.dto.request.RegularUser_Signup_DTO;
import com.book_my_show.Book.My.Show.models.ApplicationUser;
import com.book_my_show.Book.My.Show.repository.ApplicationUser_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularUserService {

    @Autowired
    ApplicationUser_Repo regularUserRepo;

    public ApplicationUser SignUp(RegularUser_Signup_DTO regularUserSignupDto) {
        ApplicationUser user = new ApplicationUser();
        user.setAge(regularUserSignupDto.getAge());
        user.setName(regularUserSignupDto.getName());
        user.setEmail(regularUserSignupDto.getEmail());
        user.setPassword(regularUserSignupDto.getPassword());
        user.setPhoneNumber(regularUserSignupDto.getPhoneNumber());
        user.setType(regularUserSignupDto.getType().toString());

        regularUserRepo.save(user);

        return user;
    }

    public ApplicationUser getUserByEmail(String email) {
        return regularUserRepo.findByEmail(email);
    }
}
