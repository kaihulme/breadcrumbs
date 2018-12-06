package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserListDAO implements UserDAO {
    private final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    public List<User> getAllUsers(){
        return users;
    }
    public User getUser(long id){
        for(User u: users){
            if(u.getId() == id) return u;
        }
        return null;
    }
    public User getByEmail(String email){
        for(User u: users){
            if(u.getEmail().equals(email)) return u;
        }
        return null;

    }
    public boolean add(User user){
        for(User u:users){
            if(u.equals(user)){
                return false;
            }
        }
        users.add(user);
        return true;

    }

    @Override
    public boolean update(long id, User u) {
        int matchIndex =-1;
        for(User user:users){
            matchIndex += 1;
            if(user.getId() == id) {
                users.set(matchIndex, u);
                return true;
            }
        }
        return false;
    }

    public boolean delete(long id){
        for(User u: users){
            if(u.getId() == id){
                users.remove(u);
                return true;
            }
        }
        return false;
    }
}
