package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Map;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.spe.breadcrumbs.web.DBConnection.getConnection;

public class MapDbDAO implements MapDAO {

    @Override
    public List<Map> getAllMaps() {
        List<Map> maps = new ArrayList<>();
        Connection con = getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Map");
            while (rs.next()) {
                Map m = new Map(rs.getLong("id"), rs.getString("name"),
                        rs.getBlob("picture"));
                maps.add(m);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maps;
    }

    @Override
    public Map getMap(Long id) {
        Connection con = getConnection();
        String getMap = "SELECT * FROM Map WHERE id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(getMap);
            stmt.setInt(1,Math.toIntExact(id));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Map m = new Map(rs.getLong("id"), rs.getString("name"),
                        rs.getBlob("picture"));
                stmt.close();
                con.close();
                return m;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addMap(Map m) {
        Connection con = getConnection();
        String addMap = "INSERT INTO Map(name,picture) VALUES(?,?)";
        try {
            PreparedStatement stmt = con.prepareStatement(addMap);
            stmt.setString(1, m.getName());
            stmt.setBlob(2, m.getPicture());
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMap(Long id, Map m) {
        Connection con = getConnection();
        String updateMap = "UPDATE Map SET name = ?, picture = ?, WHERE id = ?;";
        try {
            PreparedStatement stmt = con.prepareStatement(updateMap);
            stmt.setString(1, m.getName());
            stmt.setBlob(2, m.getPicture());
            stmt.setLong(3, m.getId());
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMap(Long id) {
        Connection con = getConnection();
        String deleteMap = "DELETE FROM Map Where id = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(deleteMap);
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
