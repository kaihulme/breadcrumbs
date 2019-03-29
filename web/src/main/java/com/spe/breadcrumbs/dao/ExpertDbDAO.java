package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.*;
import com.spe.breadcrumbs.web.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ExpertDbDAO implements ExpertDAO {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @Override
    public Expert getExpert(Long id) {
        try{
            Connection con = new DBConnection().getConnection();
            String getExpert = "SELECT * FROM Expert WHERE id = ?";
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Expert> getAllExperts(){
        List<Expert> experts = new ArrayList<>();
        try {
            Connection con = new DBConnection().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Expert");
            while (rs.next()) {
                Expert e = new Expert(rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email")
                        , rs.getString("password"));
                experts.add(e);
            }
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return experts;
    }

    @Override
    public List<Expert> getExpertsWithQuizzes() {
        List<Expert> experts = new ArrayList<>();
        try{
            Connection con = new DBConnection().getConnection();
            String getExperts = "SELECT Expert.id as expertId," +
                    "Expert.firstName as expert_FirstName," +
                    "Expert.lastName as expert_LastName," +
                    "Expert.email as expert_email," +
                    "Quiz.quizId as quizId " +
                    "FROM Expert INNER JOIN Quiz " +
                    "ON Quiz.expertId = Expert.id";
            PreparedStatement stmt = con.prepareStatement(getExperts);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Long expertId = rs.getLong("expertId");
                int expertIndex = findExpertIsInList(expertId,experts);
                Expert expert;
                if(expertIndex == -1){
                    expert = new Expert(rs.getLong("expertId"),rs.getString("expert_FirstName"),
                            rs.getString("expert_LastName"),rs.getString("expert_email"),null);
                    experts.add(expert);
                    expertIndex = experts.size() - 1;
                }
                expert = experts.get(expertIndex);
                int quizId = rs.getInt("quizId");
                QuizDAO quizDAO = new QuizDbDAO();
                Quiz quiz = quizDAO.getQuiz(quizId);
                List<User> users = quizDAO.getUsers(quizId);
                quiz.setUsers(users);
                expert.addQuiz(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return experts;
    }

    @Override
    public Expert findByEmail(String email) {
        Expert e;
        try{
            Connection con = new DBConnection().getConnection();
            String getExpert = "SELECT * FROM Expert WHERE email = ?";
            PreparedStatement stmt = con.prepareStatement(getExpert);
            stmt.setString(1,email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                e = new Expert(rs.getLong("id"),rs.getString("firstName"),
                        rs.getString("lastName"),rs.getString("email"),
                        rs.getString("password"));
                List<Role> roles = getRoles(rs.getLong("id"));
                e.setRoles(roles);
                return e;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private List<Role> getRoles(Long id){
        List<Role> roles = new ArrayList<>();
        try{
            Connection con = new DBConnection().getConnection();
            String getRoles = "SELECT * FROM Expert_Role WHERE expertId = ?";
            PreparedStatement stmt = con.prepareStatement(getRoles);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Long roleId = rs.getLong("roleId");
                Role role = getRole(roleId);
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return roles;
    }

    private Role getRole(Long id){
        try{
            Connection con = new DBConnection().getConnection();
            String getRole = "SELECT * FROM Role WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(getRole);
            stmt.setLong(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int findExpertIsInList(Long expertId,List<Expert> experts){
        for(int i = 0; i < experts.size(); i++){
            if(expertId.equals(experts.get(i).getId())) return i;
        }
        return -1;
    }

    @Override
    public boolean addExpert(Expert e) {
        try {
            Connection con = new DBConnection().getConnection();
            String addExpert = "INSERT INTO Expert(firstName,lastName,email,password) VALUES(?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(addExpert);
            stmt.setString(1,e.getFirstName());
            stmt.setString(2,e.getLastName());
            stmt.setString(3,e.getEmail());
            String password = bCryptPasswordEncoder.encode(e.getPassword());
            stmt.setString(4,password);
            stmt.executeUpdate();
            con.close();
            return true;
        } catch (SQLException | IOException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Long id, Expert e) {
        try{
            String updateExpert = "UPDATE Expert " +
                    "SET firstName = ?," +
                    "lastName = ?," +
                    "email = ?," +
                    "password = ? " +
                    "WHERE id = ?;";
            Connection con = new DBConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(updateExpert);
            stmt.setString(1,e.getFirstName());
            stmt.setString(2,e.getLastName());
            stmt.setString(3,e.getEmail());
            String password = bCryptPasswordEncoder.encode(e.getPassword());
            stmt.setString(4,password);
            stmt.setLong(5,e.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException |IOException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteExpert(Long id) {
        try{
            Connection con = new DBConnection().getConnection();
            String deleteExpert = "DELETE FROM Expert Where id = ?";
            PreparedStatement stmt = con.prepareStatement(deleteExpert);
            stmt.setLong(1,id);
            stmt.executeUpdate();
            con.close();
            return true;
        }catch (SQLException | IOException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean validate(String email, String password) {
        try{
            Connection con = new DBConnection().getConnection();
            String validateExpert = "SELECT * FROM Expert WHERE email = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(validateExpert);
            stmt.setString(1,email);
            stmt.setString(2,password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return true;
        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
