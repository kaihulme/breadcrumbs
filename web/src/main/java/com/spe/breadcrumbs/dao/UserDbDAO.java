package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
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
        List<User> users = new ArrayList<>();
        Connection con = getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()){
                User u = new User(rs.getLong("id"),rs.getString("firstName"),
                        rs.getString("lastName"),rs.getString("email"),
                        rs.getString("code"),rs.getInt("score"));
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
               u = new User(rs.getLong("id"), rs.getString("firstName"),
                       rs.getString("lastName"), rs.getString("email"),
                       rs.getString("code"),rs.getInt("score"));
               con.close();
               return u;
           }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserWithQuiz(Long id) {
        User u = null;
        Quiz quiz;
        List<Question> questions = new ArrayList<>();
        Connection con = getConnection();
        try{
            String getUser = "SELECT User.id as userId," +
                    "User.firstName as firstName," +
                    "User.lastName as lastName," +
                    "User.email as email," +
                    "User.code as code," +
                    "User.score as score," +
                    "Quiz.quizId as quizId," +
                    "Quiz.title as title," +
                    "Question.id as questionId," +
                    "Question.question as question, " +
                    "Question.x_coord as x_coord, " +
                    "Question.y_coord as y_coord " +
                    "FROM User INNER JOIN Question " +
                    "INNER JOIN Quiz ON User.quizId = Question.quizId " +
                    "WHERE User.id = ?;";
            PreparedStatement stmt = con.prepareStatement(getUser);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                u = new User(rs.getLong("userId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("code"),
                        rs.getInt("score"));
                quiz = new Quiz(rs.getLong("quizId"),rs.getString("title"));
                Question q = new Question(rs.getLong("questionId"),rs.getString("question"),
                        rs.getInt("x_coord"), rs.getInt("y_coord"));
                questions.add(q);
                quiz.setQuestions(questions);
                u.setQuiz(quiz);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public Quiz getQuiz(Long id) {
        Quiz q = null;
        try{
            Connection con = getConnection();
            String getQuiz = "SELECT quizId FROM User WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(getQuiz);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int quizId = rs.getInt("quizId");
                QuizDAO quizDAO = new QuizDbDAO();
                q = quizDAO.getQuiz(quizId);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return q;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User getByCode(String code) {
        User u = null;
        Quiz quiz;
        List<Question> questions = new ArrayList<>();
        try{
            Connection con = getConnection();
            String getUser = "SELECT User.id as userId," +
                    "User.firstName as firstName,"+
                    "User.lastName as lastName," +
                    "User.email as email," +
                    "User.code as code," +
                    "User.score as score," +
                    "Quiz.quizId as quizId," +
                    "Quiz.title as title," +
                    "Question.id as questionId," +
                    "Question.question as question " +
                    "FROM User INNER JOIN Question " +
                    "INNER JOIN Quiz ON User.quizId = Question.quizId " +
                    "WHERE User.code = ?;";
            PreparedStatement stmt = con.prepareStatement(getUser);
            stmt.setString(1,code);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                u = new User(rs.getLong("userId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("code"),
                        rs.getInt("score"));
                quiz = new Quiz(rs.getLong("quizId"),rs.getString("title"));
                Question q = new Question(rs.getLong("questionId"),rs.getString("question"),
                        rs.getInt("x_coord"), rs.getInt("y_coord"));
                questions.add(q);
                quiz.setQuestions(questions);
                u.setQuiz(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public boolean update(Long id, User u) {
        try{
            String updateUser = "UPDATE User " +
                    "SET firstName = ?," +
                    "lastName = ?," +
                    "email = ?," +
                    "code = ?," +
                    "score = ? " +
                    "WHERE id = ?;";
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(updateUser);
            stmt.setString(1,u.getFirstName());
            stmt.setString(2,u.getLastName());
            stmt.setString(3,u.getEmail());
            stmt.setString(4,u.getCode());
            stmt.setInt(5,u.getScore());
            stmt.setLong(6,u.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUser(User u) {
        try{
            Connection con = getConnection();
            //quiz ID is set to one as there will only be one quiz
            String addUser = "INSERT INTO User(firstName,lastName,email,code,quizId) VALUES(?,?,?,?,1)";
            PreparedStatement stmt = con.prepareStatement(addUser);
            stmt.setString(1,u.getFirstName());
            stmt.setString(2,u.getLastName());
            stmt.setString(3,u.getEmail());
            stmt.setString(4,u.getCode());
            stmt.executeUpdate();
            con.close();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        try{
            Connection con = getConnection();
            String deleteUser = "DELETE FROM User Where id = ?";
            PreparedStatement stmt = con.prepareStatement(deleteUser);
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
    public List<User> search(String s) {
        return null;
    }
}
