package bristol.ac.uk.breadcrumbsspe.entity;

import java.sql.Time;

public class Meeting {
    private Expert expert;
    private User user;
    private Time meetingTime;
    private String location;

    public Meeting(Expert e, User u, Time t, String loc){
        expert = e;
        user = u;
        meetingTime = t;
        location = loc;
    }

    public User getUser() {
        return user;
    }

    public Expert getExpert() {
        return expert;
    }

    public Time getMeetingTime() {
        return meetingTime;
    }

    public String getLocation() {
        return location;
    }
}
