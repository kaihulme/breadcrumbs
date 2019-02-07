package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.spe.breadcrumbs.web.DBConnection.getConnection;

public class UserDbDAO implements UserDAO {

    @Override
    public List<User> getAllUsers(){
      //  if(!userCache.isEmpty()) return userCache;
        List<User> users = new ArrayList<>();
        Connection con = getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()){
                User u = new User(rs.getLong("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("email"));
                users.add(u);
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
           if(rs.next()) { //move it to the first row
               u = new User(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"));
               con.close();
               return u;
           }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public boolean addUser(User u) {
        try{
            Connection con = getConnection();
            String addUser = "INSERT INTO User(firstName,lastName,email) VALUES(?,?,?)";
            PreparedStatement stmt = con.prepareStatement(addUser);
            stmt.setString(1,u.getFirstName());
            stmt.setString(2,u.getLastName());
            stmt.setString(3,u.getEmail());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, User u) {
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        try{
            Connection con = getConnection();
            String deleteUser = "DELETE FROM User Where id = ?";
            PreparedStatement stmt = con.prepareStatement(deleteUser);
            stmt.setLong(1,id);
            stmt.executeUpdate();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> search(String s) {
        return null;
    }
}
