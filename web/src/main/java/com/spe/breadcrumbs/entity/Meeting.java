package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;

@Getter
@Setter
public class Meeting {

    private Expert expert;
    private Long expertId;

    private User user;
    private Long userId;

    @DateTimeFormat (pattern="HH:mm:ss")
    private Time meeting_time;
    private String time;

    private String location;

    public Meeting(Expert e, User u, Time t, String loc){
        expert = e;
        user = u;
        meeting_time = t;
        location = loc;
    }
    public Meeting(){}

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
