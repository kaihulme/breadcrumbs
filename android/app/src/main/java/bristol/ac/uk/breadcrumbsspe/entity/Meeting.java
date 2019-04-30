package bristol.ac.uk.breadcrumbsspe.entity;

import java.sql.Time;

public class Meeting {
    private String expertName;
    private User user;
    private Time meetingTime;
    private String location;

    public Meeting(String expertName, User user, Time time, String location){
        this.expertName = expertName;
        this.user = user;
        this.meetingTime = time;
        this.location = location;
    }

    public String getExpertName(){
        return expertName;
    }

    public User getUser() {
        return user;
    }

    public Time getMeetingTime() {
        return meetingTime;
    }

    public String getLocation() {
        return location;
    }
}
