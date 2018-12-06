package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.entity.User;
import org.springframework.data.repository.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User,Long> {

    User findByEmail(String email);
    List<User> findByFirstName(String firstName);

    void addUser(@RequestParam("firstName") String  firstName,@RequestParam("lastName") String lastName,@RequestParam("email") String email);
}
