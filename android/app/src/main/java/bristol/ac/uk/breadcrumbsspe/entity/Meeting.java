package bristol.ac.uk.breadcrumbsspe.entity;

import java.sql.Time;

public class Meeting {
    private Expert expert;
    private User user;
    private Time meeting_time;
    private String location;

    public Meeting(Expert e, User u, Time t, String loc){
        expert = e;
        user = u;
        meeting_time = t;
        location = loc;
    }

    public User getUser() {
        return user;
    }

    public Expert getExpert() {
        return expert;
    }

    public Time getMeeting_time() {
        return meeting_time;
    }

    public String getLocation() {
        return location;
    }
}
