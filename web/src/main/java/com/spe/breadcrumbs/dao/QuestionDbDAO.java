package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.web.DBConnection;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QuestionDbDAO implements QuestionDAO {

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        try{
            Connection con = new DBConnection().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Question");
            while (rs.next()){
                Question q = new Question(rs.getLong("id"),rs.getString("question"),
                             rs.getInt("x_coord"), rs.getInt("y_coord"));
                questions.add(q);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //add choices
        for(Question q: questions){
            List<Choice> c = getChoices(q.getId());
            q.setChoices(c);
        }
        return questions;
    }

    @Override
    public List<Question> find(String t) {
        return null;
    }

    @Override
    public Question findById(Long id) {
        Question q = null;
        try{
            Connection con = new DBConnection().getConnection();
            String getQuestion = "SELECT * FROM Question WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(getQuestion);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                //move it to the first row
                q = new Question(rs.getLong("id"),rs.getString("question"),
                        rs.getInt("x_coord"), rs.getInt("y_coord"));
                con.close();
                List<Choice> choices = getChoices(q.getId());
                q.setChoices(choices);
            }
        }catch(SQLException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return q;
    }

    @Override
    public Question findByCode(String code) {
        Question q = null;
        try{
            Connection con = new DBConnection().getConnection();
            String getQuestion = "SELECT * FROM Question WHERE code = ?";
            PreparedStatement stmt = con.prepareStatement(getQuestion);
            stmt.setString(1,code);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                q = new Question(rs.getLong("id"),rs.getString("question"),
                        rs.getInt("x_coord"), rs.getInt("y_coord"));
                con.close();
                List<Choice> choices = getChoices(q.getId());
                q.setChoices(choices);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return q;
    }

    @Override
    public List<Choice> getChoices(Long questionId) {
        List<Choice> choices = new ArrayList<>();
        try{
            Connection con = new DBConnection().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Choice");
            while(rs.next()){
                if(rs.getLong("question") == questionId){
                    Choice c = new Choice(questionId,rs.getString("choiceText"),rs.getBoolean("answer"));
                    c.setChoiceId(rs.getLong("id"));
                    choices.add(c);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return choices;
    }

    @Override
    public boolean update(Long id, Question q) {
        try{
            String updateQuestion = "UPDATE Question " +
                    "SET question = ?," +
                    "x_coord = ?," +
                    "y_coord = ? " +
                    "WHERE id = ?;";
            Connection con = new DBConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(updateQuestion);
            stmt.setString(1,q.getQuestion());
            stmt.setInt(2, q.getX_coord());
            stmt.setInt(3, q.getY_coord());
            stmt.setLong(4,q.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

}
