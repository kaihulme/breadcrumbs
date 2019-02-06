package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Expert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            rs.next(); //move it to the first row
            Expert e = new Expert(rs.getLong("id"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("email")
                                  ,rs.getString("password"));
        con.close();
            return e;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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
