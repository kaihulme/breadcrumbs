package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserDbDAO implements UserDAO {

    private UserRepository userRepo;
    private List<User> users = new ArrayList<>();

    @Override
    public List<User> getAllUsers() {
        for(User u: userRepo.findAll()){
            users.add(u);
        }
        return users;
    }

    @Override
    public User getUser(Long id) {
        return userRepo.findOne(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public boolean add(User user) {
        for(User u: userRepo.findAll()){
            if(u.equals(user)) return false;
        }
        userRepo.addUser(user.getFirstName(),user.getLastName(),user.getEmail());
        return true;
    }

    @Override
    public boolean update(Long id, User u) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if(userRepo.findOne(id) != null){
            userRepo.delete(userRepo.findOne(id));
            return true;
        }
        return false;
    }
}
