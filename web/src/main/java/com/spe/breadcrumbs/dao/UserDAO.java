package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUser(Long id);

    //gets the User with the quiz object
    User getUserWithQuiz(Long id);
    //get quiz that user is taking
    Quiz getQuiz(Long id);
    User getByEmail(String email);
    User getByCode(String code);
    boolean addUser(User u);
    boolean update(Long id,User u);
    boolean deleteUser(Long id);
    //gets all users whose first or last name contain s
    List<User> search(String s);
}
