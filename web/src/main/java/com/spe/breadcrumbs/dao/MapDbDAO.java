package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Map;
import com.spe.breadcrumbs.web.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapDbDAO implements MapDAO {

    private DBConnection dbConnection;
    public MapDbDAO(DBConnection d){
        dbConnection = d;
    }

    @Override
    public List<Map> getAllMaps() {
        List<Map> maps = new ArrayList<>();
        try {
            Connection con = dbConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Map");
            while (rs.next()) {
                Map m = new Map(rs.getLong("questionId"), rs.getString("name"),
                        rs.getBlob("picture"));
                maps.add(m);
            }
            con.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return maps;
    }

    @Override
    public Map getMap(Long id) {

        String getMap = "SELECT * FROM Map WHERE id = ?";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(getMap);
            stmt.setInt(1, Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Map m = new Map(rs.getLong("questionId"), rs.getString("name"),
                        rs.getBlob("picture"));
                stmt.close();
                con.close();
                return m;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map getMapByName(String name) {
        String getMapByName = "SELECT * FROM Map WHERE name = ?";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(getMapByName);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Map m = new Map(rs.getLong("questionId"), rs.getString("name"),
                        rs.getBlob("picture"));
                stmt.close();
                con.close();
                return m;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addMap(Map m) {
        String addMap = "INSERT INTO Map(questionId, name, picture) VALUES(?,?,?)";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(addMap);

            Long questionId = m.getQuestionId();
            if (questionId == 0) stmt.setNull(1, Types.INTEGER);
            else stmt.setLong(1, m.getQuestionId());

            stmt.setString(2, m.getName());
            stmt.setBlob(3, m.getPicture());
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMap(Long id, Map m) {
        String updateMap = "UPDATE Map SET questionID = ?, name = ?, picture = ? WHERE id = ?;";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateMap);
            stmt.setLong(1, m.getQuestionId());
            stmt.setString(2, m.getName());
            stmt.setBlob(3, m.getPicture());
            stmt.setLong(4, m.getId());
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMapByName(String name, Map m) {
        String updateMapByName = "UPDATE Map SET questionId = ?, picture = ? WHERE name = ?;";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateMapByName);
            stmt.setLong(1, m.getQuestionId());
            stmt.setBlob(2, m.getPicture());
            stmt.setString(3, name);
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMap(Long id) {
        String deleteMap = "DELETE FROM Map Where id = ?";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteMap);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMapsForQuestion(Long question_id) {
        String deleteMap = "DELETE FROM Map Where questionId = ?";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteMap);
            stmt.setLong(1, question_id);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAllMaps() {
        String deleteAllMaps = "TRUNCATE Map";
        try {
            Connection con = dbConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteAllMaps);
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
