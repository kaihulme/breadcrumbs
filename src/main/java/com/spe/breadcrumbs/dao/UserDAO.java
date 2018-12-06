package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;
import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUser(Long id);
    User getByEmail(String email);
    boolean add(User u);
    boolean update(Long id,User u);
    boolean delete(Long id);
}
