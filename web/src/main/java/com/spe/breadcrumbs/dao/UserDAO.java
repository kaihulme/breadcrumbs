package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUser(Long id);
    User getByEmail(String email);
    boolean addUser(User u);
    boolean update(Long id,User u);
    boolean deleteUser(Long id);
    //gets all users whose first or last name contain s
    List<User> search(String s);
}
