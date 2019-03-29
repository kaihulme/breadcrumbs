package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.DBConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuizDbDAO implements QuizDAO{

    @Override
    public Quiz getQuiz(int id) {
        Quiz q = null;
        try{
            Connection con = new DBConnection().getConnection();
            String getQuiz = "SELECT * FROM Quiz WHERE quizId = ?";
            PreparedStatement stmt = con.prepareStatement(getQuiz);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                q = new Quiz(rs.getLong("quizId"),rs.getString("title"));
                con.close();
                List<Question> questions = getQuestions(q.getId());
                q.setQuestions(questions);
            }
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return q;
    }

    @Override
    public List<Question> getQuestions(Long id) {
        List<Question> questions = new ArrayList<>();
        try{
            Connection con = new DBConnection().getConnection();
            String getQuestions = "SELECT * FROM Question WHERE quizId = ?";
            PreparedStatement stmt = con.prepareStatement(getQuestions);
            stmt.setLong(1,id);
            ResultSet rs =stmt.executeQuery();
            while(rs.next()){
                Question q = new Question(rs.getLong("id"),rs.getString("question"),
                        rs.getInt("x_coord"), rs.getInt("y_coord"));
                questions.add(q);
            }
            con.close();
            for(Question q: questions){
                //get choices for question
                List<Choice> choices = new QuestionDbDAO().getChoices(q.getId());
                q.setChoices(choices);
            }
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<User> getUsers(int id){
        List<User> users = new ArrayList<>();
        try{
            Connection con = new DBConnection().getConnection();
            String getUsers = "SELECT * FROM User WHERE quizId = ?";
            PreparedStatement stmt = con.prepareStatement(getUsers);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                User u = new User(rs.getLong("id"),rs.getString("firstName"),
                        rs.getString("lastName"),rs.getString("email"),rs.getString("code"),rs.getInt("score"));
                users.add(u);
            }
            con.close();
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return users;
    }
}
