package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
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