package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Hint;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.web.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HintDbDAO implements HintDAO {
    private DBConnection dbConnection;

    public HintDbDAO(DBConnection d){
        dbConnection = d;
    }

    @Override
    public Hint getHintByName(String name) {
        String getHintByName = "SELECT * FROM Hint WHERE pictureName = ?";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(getHintByName);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Hint hint = new Hint(rs.getLong("id"),rs.getString("hintText"),
                        rs.getInt("x_coord"),rs.getInt("y_coord"),
                        rs.getString("pictureName"), rs.getBlob("picture"));
                QuestionDAO questionDAO = new QuestionDbDAO(dbConnection);
                Question question = questionDAO.getQuestion(rs.getLong("question"));
                hint.setQuestion(question);
                hint.setCode(rs.getString("code"));
                stmt.close();
                return hint;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Hint getHintByCode(String code) {
        String getHintByCode = "SELECT * FROM Hint WHERE code = ?";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(getHintByCode);
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Hint hint = new Hint(rs.getLong("id"),rs.getString("hintText"),
                        rs.getInt("x_coord"),rs.getInt("y_coord"),
                        rs.getString("pictureName"), rs.getBlob("picture"));
                QuestionDAO questionDAO = new QuestionDbDAO(dbConnection);
                Question question = questionDAO.getQuestion(rs.getLong("question"));
                hint.setQuestion(question);
                hint.setQuestionId(rs.getLong("question"));
                hint.setCode(code);
                stmt.close();
                return hint;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Hint getHintById(Long id) {
        String getHintByCode = "SELECT * FROM Hint WHERE id = ?";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(getHintByCode);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Hint hint = new Hint(rs.getLong("id"),rs.getString("hintText"),
                        rs.getInt("x_coord"),rs.getInt("y_coord"),
                        rs.getString("pictureName"), rs.getBlob("picture"));
                QuestionDAO questionDAO = new QuestionDbDAO(dbConnection);
                Question question = questionDAO.getQuestion(rs.getLong("question"));
                hint.setQuestion(question);
                hint.setQuestionId(rs.getLong("question"));
                hint.setCode(rs.getString("code"));
                stmt.close();
                return hint;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean addHint(Hint h, Long question_id) {
        try{
            Connection con = dbConnection.getConnection();
            String addHint = "INSERT INTO Hint(question, hintText,x_coord,y_coord) VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(addHint);
            stmt.setLong(1,question_id);
            stmt.setString(2,h.getHintText());
            stmt.setInt(3,h.getX_coord());
            stmt.setInt(4,h.getY_coord());
            stmt.executeUpdate();
//            con.close();
            return true;
        }catch(SQLException | IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteHint(Long hint_id) {
        try{
            Connection con = dbConnection.getConnection();
            String deleteHint = "DELETE FROM Hint Where id = ?";
            PreparedStatement stmt = con.prepareStatement(deleteHint);
            stmt.setLong(1,hint_id);
            stmt.executeUpdate();
            return true;
        }catch (SQLException | IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateHint(Hint h, Long id) {
        try{
            String updateUser = "UPDATE Hint " +
                    "SET hintText = ? " +
                    "WHERE id = ?;";
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateUser);
            stmt.setString(1,h.getHintText());
            stmt.setLong(2,id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateHintLocation(Hint h, Long id) {
        try{
            String updateUser = "UPDATE Hint " +
                    "SET x_coord = ?, y_coord = ? " +
                    "WHERE id = ?;";
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateUser);
            stmt.setInt(1,h.getX_coord());
            stmt.setInt(2,h.getY_coord());
            stmt.setLong(3,id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateHintImage(String pictureName, Blob picture, Long id) {
        try{
            String updateUser = "UPDATE Hint " +
                    "SET pictureName = ?, picture = ? " +
                    "WHERE id = ?;";
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateUser);
            stmt.setString(1, pictureName);
            stmt.setBlob(2, picture);
            stmt.setLong(3, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeHintImage(Long hint_id) {
        try{

            String updateUser = "UPDATE Hint " +
                    "SET pictureName = ?, picture = ? " +
                    "WHERE id = ?;";
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateUser);
            stmt.setNull(1, Types.INTEGER);
            stmt.setNull(2, Types.INTEGER);
            stmt.setLong(3, hint_id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
