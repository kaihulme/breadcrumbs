package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUser(long id);
    User getByEmail(String email);
    boolean add(User u);
    boolean update(long id,User u);
    boolean delete(long id);
}
