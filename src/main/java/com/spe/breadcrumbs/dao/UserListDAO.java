package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;
import sun.text.normalizer.UTF16;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.spe.breadcrumbs.dao.TestUserList.getTestUsers;

public class UserListDAO implements UserDAO {
    private final CopyOnWriteArrayList<User> users = getTestUsers() ;
    public List<User> getAllUsers(){
        return users;
    }
    public User getUser(Long id){
        for(User u: users){
            if(u.getId().equals(id)) return u;
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
    public boolean update(Long id, User u) {
        int matchIndex =-1;
        for(User user:users){
            matchIndex += 1;
            if(user.getId().equals(id)) {
                users.set(matchIndex, u);
                return true;
            }
        }
        return false;
    }

    public boolean delete(Long id){
        for(User u: users){
            if(u.getId().equals(id)){
                users.remove(u);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> search(String s) {
        List<User> matches = new ArrayList<>();
        for(User u: users){
            if(u.getFirstName().toLowerCase().contains(s.toLowerCase())||u.getLastName().toLowerCase().contains(s.toLowerCase())) matches.add(u);
        }
        return matches;
    }
}
