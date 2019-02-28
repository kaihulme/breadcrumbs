package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.spe.breadcrumbs.web.DBConnection.getConnection;

public class QuestionDbDAO implements QuestionDAO {

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Connection con = getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Question");
            while (rs.next()){
                Question q = new Question(rs.getLong("id"),rs.getString("question"));
                questions.add(q);
            }
        }catch(SQLException e) {
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
        Connection con = getConnection();
        try{
            String getQuestion = "SELECT * FROM Question WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(getQuestion);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                //move it to the first row
                q = new Question(rs.getLong("id"),rs.getString("question"));
                con.close();
                List<Choice> choices = getChoices(q.getId());
                q.setChoices(choices);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return q;
    }

    @Override
    public List<Choice> getChoices(Long questionId) {
        List<Choice> choices = new ArrayList<>();
        Connection con = getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Choice");
            while(rs.next()){
                if(rs.getLong("question") == questionId){
                    Choice c = new Choice(questionId,rs.getString("choiceText"),rs.getBoolean("answer"));
                    choices.add(c);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return choices;
    }

    @Override
    public boolean update(Long id, Question q) {
        try{
            String updateQuestion = "UPDATE Question " +
                    "SET question = ?" +
                    "WHERE id = ?;";
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(updateQuestion);
            stmt.setString(1,q.getQuestion());
            stmt.setLong(2,q.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
