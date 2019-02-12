package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Expert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.spe.breadcrumbs.web.DBConnection.getConnection;

public class ExpertDbDAO implements ExpertDAO {

    @Override
    public Expert getExpert(Long id) {
        try{
            Connection con = getConnection();
            String getExpert = "SELECT * FROM Experts WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(getExpert);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                //move it to the first row
                Expert e = new Expert(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email")
                        , rs.getString("password"));
                con.close();
                return e;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Expert> getAllExperts(){
        List<Expert> experts = new ArrayList<>();
        Connection con = getConnection();
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Experts");
            while (rs.next()) {
                Expert e = new Expert(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email")
                        , rs.getString("password"));
                experts.add(e);
            }
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return experts;
    }

    @Override
    public boolean addExpert(Expert e) {
        try {
            Connection con = getConnection();
            String addExpert = "INSERT INTO Experts(firstName,lastName,email,password) VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(addExpert);
            stmt.setString(1,e.getFirstName());
            stmt.setString(2,e.getLastName());
            stmt.setString(3,e.getEmail());
            stmt.setString(4,e.getPassword());
            stmt.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Expert e) {
        try{
            String updateExpert = "UPDATE Experts " +
                    "SET firstName = ?," +
                    "lastName = ?," +
                    "email = ?," +
                    "password = ? " +
                    "WHERE id = ?;";
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(updateExpert);
            stmt.setString(1,e.getFirstName());
            stmt.setString(2,e.getLastName());
            stmt.setString(3,e.getEmail());
            stmt.setString(4,e.getPassword());
            stmt.setLong(5,e.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteExpert(Long id) {
        try{
            Connection con = getConnection();
            String deleteExpert = "DELETE FROM Experts Where id = ?";
            PreparedStatement stmt = con.prepareStatement(deleteExpert);
            stmt.setLong(1,id);
            stmt.executeUpdate();
            con.close();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validate(String email, String password) {
        try{
            Connection con = getConnection();
            String validateExpert = "SELECT * FROM Experts WHERE email = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(validateExpert);
            stmt.setString(1,email);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
