package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Meeting;
import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingDbDAO implements MeetingDAO {
    private DBConnection dbConnection;
    public MeetingDbDAO(DBConnection dbConnection){
        this.dbConnection = dbConnection;
    }
    @Override
    public boolean createMeeting(Meeting m) {
        try{
            Connection con = dbConnection.getConnection();
            String addMeeting = "INSERT INTO Meeting (userId,expertId,meeting_time,location) VALUES (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(addMeeting);
            stmt.setLong(1,m.getUser().getId());
            stmt.setLong(2,m.getExpert().getId());
            stmt.setTime(3,m.getMeeting_time());
            stmt.setString(4,m.getLocation());
            stmt.executeUpdate();
            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMeeting(Long userId, Long expertId, Meeting m) {
        try{
            Connection con = dbConnection.getConnection();
            String updateMeeting = "UPDATE Meeting SET meeting_time = ?, location = ? WHERE userId = ? AND expertId = ?";
            PreparedStatement stmt = con.prepareStatement(updateMeeting);
            stmt.setTime(1,m.getMeeting_time());
            stmt.setString(2,m.getLocation());
            stmt.setLong(3,userId);
            stmt.setLong(4,expertId);
            stmt.executeUpdate();

            System.out.println(m.getLocation());

            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMeeting(Long userId, Long expertId) {
        try{
            Connection con = dbConnection.getConnection();
            String deleteMeeting = "DELETE FROM Meeting WHERE userId = ? AND expertId = ?";
            PreparedStatement stmt = con.prepareStatement(deleteMeeting);
            stmt.setLong(1,userId);
            stmt.setLong(2,expertId);
            stmt.executeUpdate();
            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Meeting> getMeetings() {
        List<Meeting> meetings = new ArrayList<>();
        UserDAO userDAO = new UserDbDAO(dbConnection);
        ExpertDAO expertDAO = new ExpertDbDAO(dbConnection);
        try{
            Connection con = dbConnection.getConnection();
            String getMeetings = "SELECT * FROM Meeting";
            PreparedStatement stmt = con.prepareStatement(getMeetings);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                User user = userDAO.getUser(rs.getLong("userId"));
                Expert expert = expertDAO.getExpert(rs.getLong("expertId"));
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                Meeting m = new Meeting(expert,user,t,loc);
                String time = t.toString();
                m.setTime(time.substring(0, 5));
                meetings.add(m);
            }
            return meetings;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Meeting> getMeetingsWithExpert(Long expertId) {
        UserDAO userDAO = new UserDbDAO(dbConnection);
        ExpertDAO expertDAO = new ExpertDbDAO(dbConnection);
        List<Meeting> meetings = new ArrayList<>();
        try{
            Connection con = dbConnection.getConnection();
            String getMeetings = "SELECT * FROM Meeting WHERE expertId = ?";
            PreparedStatement stmt = con.prepareStatement(getMeetings);
            stmt.setLong(1,expertId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user = userDAO.getUser(rs.getLong("userId"));
                Expert expert = expertDAO.getExpert(rs.getLong("expertId"));
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                Meeting m = new Meeting(expert,user,t,loc);
                meetings.add(m);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return meetings;
    }

    @Override
    public Meeting getMeeting(Long userId, Long expertId) {
        UserDAO userDAO = new UserDbDAO(dbConnection);
        ExpertDAO expertDAO = new ExpertDbDAO(dbConnection);
        Meeting m = null;
        try{
            Connection con = dbConnection.getConnection();
            String getMeetings = "SELECT * FROM Meeting WHERE userId = ? AND expertId = ?";
            PreparedStatement stmt = con.prepareStatement(getMeetings);
            stmt.setLong(1,userId);
            stmt.setLong(2,expertId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                User user = userDAO.getUser(rs.getLong("userId"));
                Expert expert = expertDAO.getExpert(rs.getLong("expertId"));
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                m = new Meeting(expert,user,t,loc);
                String time = t.toString();
                m.setTime(time.substring(0, 5));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

}
