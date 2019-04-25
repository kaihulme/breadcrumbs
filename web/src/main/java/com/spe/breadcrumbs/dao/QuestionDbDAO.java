package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Hint;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.web.DBConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QuestionDbDAO implements QuestionDAO {
    private DBConnection dbConnection;
    public QuestionDbDAO(DBConnection d){
        dbConnection = d;
    }

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        try{
            Connection con = dbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Question");
            while (rs.next()){
                Question q = new Question(rs.getLong("id"),rs.getString("question"),
                             rs.getInt("x_coord"), rs.getInt("y_coord"));
                questions.add(q);
            }
        }catch(SQLException | IOException e) {
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
            Connection con = dbConnection.getConnection();
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
        }catch(SQLException | IOException e){
            e.printStackTrace();
        }
        return q;
    }

    @Override
    public List<Question> getQuestionsAnswered(Long userId) {
        List<Question> questionsAnswered = new ArrayList<>();
        try{
            Connection con = dbConnection.getConnection();
            String getQuestionsAnswered = "SELECT question FROM Attempt INNER JOIN Choice on Choice.id = Attempt.choiceId " +
                    "WHERE Choice.answer = true AND Attempt.userId = ?";
            PreparedStatement stmt = con.prepareStatement(getQuestionsAnswered);
            stmt.setLong(1,userId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Question q = findById(rs.getLong("question"));
                questionsAnswered.add(q);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return null;
        }
        return questionsAnswered;
    }

    @Override
    public Question findByCode(String code) {
        Question q = null;
        try{
            Connection con = dbConnection.getConnection();
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return q;
    }

    @Override
    public List<Choice> getChoices(Long questionId) {
        List<Choice> choices = new ArrayList<>();
        try{
            Connection con = dbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Choice");
            while(rs.next()){
                if(rs.getLong("question") == questionId){
                    Choice c = new Choice(questionId,rs.getString("choiceText"),rs.getBoolean("answer"));
                    c.setChoiceId(rs.getLong("id"));
                    choices.add(c);
                }
            }
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return choices;
    }

    /////////////////// HINTS /////////////////////////

    @Override
    public List<Hint> getHints(Long questionId) {
        List<Hint> hints = new ArrayList<>();
        try{
            Connection con = dbConnection.getConnection();
            String getHints = "SELECT * FROM Hint WHERE question = ?";
            PreparedStatement stmt = con.prepareStatement(getHints);
            stmt.setLong(1,questionId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Hint h = new Hint(rs.getLong("id"),rs.getString("hintText"),rs.getInt("x_coord"),rs.getInt("y_coord"),rs.getBlob("picture"));
                hints.add(h);
            }
            return hints;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    /////////////// UPDATE /////////////////////

    @Override
    public boolean updateChoices( Long questionId, List<Choice> choices ) {

        List<Long> updatedChoices = new ArrayList<>();

        for(Choice c: choices){
            if(!updateChoice(c.getChoiceId(),c)){
                return false;
            }
        }
        return true;
    }
    private boolean updateChoice(Long choiceId, Choice c){
        try{
            String updateChoice = "UPDATE Choice SET choiceText = ?," +
                    "answer = ? WHERE id = ?";
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateChoice);
            stmt.setString(1,c.getChoiceText());
            stmt.setBoolean(2,c.isAnswer());
            stmt.setLong(3,choiceId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException| IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Question q) {
        try{
            String updateQuestion = "UPDATE Question " +
                    "SET question = ?" +
                    "WHERE id = ?;";
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateQuestion);
            stmt.setString(1,q.getQuestion());
            stmt.setLong(2,q.getId());
            stmt.executeUpdate();
            updateChoices(id,q.getChoices());
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateLocation(Long id, Question q) {
        try{
            String updateQuestion = "UPDATE Question " +
                    "SET x_coord = ?," +
                    "y_coord = ? " +
                    "WHERE id = ?;";
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateQuestion);
            stmt.setInt(1, q.getX_coord());
            stmt.setInt(2, q.getY_coord());
            stmt.setLong(3,q.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
