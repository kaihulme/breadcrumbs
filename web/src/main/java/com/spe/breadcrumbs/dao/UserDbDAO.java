package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.spe.breadcrumbs.web.DBConnection.getConnection;

public class UserDbDAO implements UserDAO {

    private UserRepository userRepo;
    private List<User> users = new ArrayList<>();
    private List<User> userCache = new ArrayList<>();

    @Override
    public List<User> getAllUsers(){
        if(!userCache.isEmpty()) return userCache;
        Connection con = getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()){
                User u = new User(rs.getLong("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("email"));
                users.add(u);
                userCache.add(u);
            }
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUser(Long id) {
        Connection con = getConnection();
        User u;
        try{
            String getUser = "SELECT * FROM User WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(getUser);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            rs.next(); //move it to the first row
            u = new User(rs.getLong("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("email"));
            con.close();
            return u;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
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

    @Override
    public List<User> search(String s) {
        return null;
    }
}
