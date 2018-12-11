package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Question;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.spe.breadcrumbs.web.DBConnection.getConnection;

public class QuestionDbDAO implements QuestionDAO {
    private List<Question> questions = new ArrayList<>();
    private List<Question> questionCache = new ArrayList<>();

    @Override
    public List<Question> getAllQuestions() {
        if (!questionCache.isEmpty()) return questionCache;
        Connection con = getConnection();
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Question");
            while (rs.next()){
                Question q = new Question(rs.getLong("id"),rs.getString("question"),0);
                questions.add(q);
                questionCache.add(q);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<Question> find(String t) {
        return null;
    }

    @Override
    public Question findById(Long id) {
        return null;
    }
}
