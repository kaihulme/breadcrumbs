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
//            String addMeeting = "INSERT INTO Meeting (userId,expertId,meeting_time,location,completed, picture) VALUES (?,?,?,?,?,?)";
            String addMeeting = "INSERT INTO Meeting (userId, expertId, meeting_time, location, completed) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(addMeeting);
            stmt.setLong(1,m.getUser().getId());
            stmt.setLong(2,m.getExpert().getId());
            stmt.setTime(3,m.getMeeting_time());
            stmt.setString(4,m.getLocation());
            stmt.setBoolean(5, false);
            //stmt.setBlob(6, m.getPicture());

            System.out.println(stmt);

            stmt.executeUpdate();
            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMeeting(Long userId, Meeting m) {
        try{
            Connection con = dbConnection.getConnection();
            String updateMeeting = "UPDATE Meeting SET expertId = ?, userId = ? WHERE userId = ?";
            PreparedStatement stmt = con.prepareStatement(updateMeeting);
            stmt.setLong(1,m.getExpertId());
            stmt.setLong(2,m.getExpertId());
            stmt.setLong(3,userId);
            stmt.executeUpdate();

            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMeetingLocation(Long userId, Meeting m) {
        try{
            Connection con = dbConnection.getConnection();
            String updateMeeting = "UPDATE Meeting SET x_coord = ?, y_coord = ?, picture = ? WHERE userId = ?";
            PreparedStatement stmt = con.prepareStatement(updateMeeting);
            stmt.setInt(1,m.getX_coord());
            stmt.setInt(2,m.getY_coord());
            stmt.setBlob(3,m.getPicture());
            stmt.setLong(4,userId);
            stmt.executeUpdate();

            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean completeMeeting(Long userId, String text) {
        try{
            Connection con = dbConnection.getConnection();
            String updateMeeting = "UPDATE Meeting SET text = ?, completed = ? WHERE userId = ?";
            PreparedStatement stmt = con.prepareStatement(updateMeeting);
            stmt.setString(1,text);
            stmt.setBoolean(2, true);
            stmt.setLong(3,userId);
            stmt.executeUpdate();

            return true;
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMeeting(Long userId) {
        try{
            Connection con = dbConnection.getConnection();
            String deleteMeeting = "DELETE FROM Meeting WHERE userId = ?";
            PreparedStatement stmt = con.prepareStatement(deleteMeeting);
            stmt.setLong(1,userId);
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
                Long userId = rs.getLong("userId");
                Long expertId = rs.getLong("expertId");
                User user = userDAO.getUser(userId);
                Expert expert = expertDAO.getExpert(expertId);
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                Boolean completed = rs.getBoolean("completed");
                Meeting m = new Meeting(userId, expertId, expert,user,t,loc, completed);
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
            while(rs.next()){
                Long userId = rs.getLong("userId");
                User user = userDAO.getUser(userId);
                Expert expert = expertDAO.getExpert(expertId);
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                Boolean completed = rs.getBoolean("completed");
                Meeting m = new Meeting(userId, expertId, expert,user,t,loc, completed);
                String time = t.toString();
                m.setTime(time.substring(0, 5));
                meetings.add(m);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return meetings;
    }

    @Override
    public List<Meeting> getUpcomingMeetingsWithExpert(Long expertId) {
        UserDAO userDAO = new UserDbDAO(dbConnection);
        ExpertDAO expertDAO = new ExpertDbDAO(dbConnection);
        List<Meeting> meetings = new ArrayList<>();
        try{
            Connection con = dbConnection.getConnection();
            String getMeetings = "SELECT * FROM Meeting WHERE expertId = ? AND completed = ?";
            PreparedStatement stmt = con.prepareStatement(getMeetings);
            stmt.setLong(1,expertId);
            stmt.setBoolean(2, false);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Long userId = rs.getLong("userId");
                User user = userDAO.getUser(userId);
                Expert expert = expertDAO.getExpert(expertId);
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                Boolean completed = rs.getBoolean("completed");
                Meeting m = new Meeting(userId, expertId, expert,user,t,loc, completed);
                String time = t.toString();
                m.setTime(time.substring(0, 5));
                meetings.add(m);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return meetings;
    }

    @Override
    public List<Meeting> getCompletedMeetingsWithExpert(Long expertId) {
        UserDAO userDAO = new UserDbDAO(dbConnection);
        ExpertDAO expertDAO = new ExpertDbDAO(dbConnection);
        List<Meeting> meetings = new ArrayList<>();
        try{
            Connection con = dbConnection.getConnection();
            String getMeetings = "SELECT * FROM Meeting WHERE expertId = ? AND completed = ?";
            PreparedStatement stmt = con.prepareStatement(getMeetings);
            stmt.setLong(1,expertId);
            stmt.setBoolean(2, true);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Long userId = rs.getLong("userId");
                User user = userDAO.getUser(userId);
                Expert expert = expertDAO.getExpert(expertId);
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                Boolean completed = rs.getBoolean("completed");
                Meeting m = new Meeting(userId, expertId, expert,user,t,loc, completed);
                String time = t.toString();
                m.setTime(time.substring(0, 5));
                meetings.add(m);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return meetings;
    }

    @Override
    public Meeting getMeeting(Long userId) {
        UserDAO userDAO = new UserDbDAO(dbConnection);
        ExpertDAO expertDAO = new ExpertDbDAO(dbConnection);
        Meeting m = null;
        try{
            Connection con = dbConnection.getConnection();
            String getMeetings = "SELECT * FROM Meeting WHERE userId = ?";
            PreparedStatement stmt = con.prepareStatement(getMeetings);
            stmt.setLong(1,userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Long expertId = rs.getLong("expertId");
                User user = userDAO.getUser(userId);
                Expert expert = expertDAO.getExpert(expertId);
                expert.setEmail(null); // email shouldn't be retrieved via api
                expert.setPassword(null); //passwords shouldn't be retrieved via api
                Time t = rs.getTime("meeting_time");
                String loc = rs.getString("location");
                Boolean completed = rs.getBoolean("completed");
                m = new Meeting(userId, expertId, expert,user,t,loc, completed);
                String time = t.toString();
                m.setTime(time.substring(0, 5));
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

}
