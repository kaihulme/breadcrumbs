package bristol.ac.uk.breadcrumbsspe.entity;

import java.sql.Time;

public class Meeting {

    private Expert expert;
    private User user;
    private Time meetingTime;
    private String location;

    public Meeting(Expert expert, User user, Time time, String location){
        this.expert = expert;
        this.user = user;
        this.meetingTime = time;
        this.location = location;
    }

    public Expert getExpert(){
        return expert;
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
