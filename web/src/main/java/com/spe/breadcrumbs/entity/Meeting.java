package com.spe.breadcrumbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.sql.Blob;
import java.sql.Time;
import java.sql.Types;

@Getter
@Setter
public class Meeting {

    @Column(name="expertId")
    private Long expertId;
    private Expert expert;

    @Column(name="userId")
    private Long userId;
    private User user;

    @DateTimeFormat (pattern="HH:mm:ss")
    @Column(name="meeting_time")
    private Time meeting_time;
    private String time;

    @Column(name="location")
    private String location;

    @Column(name="x_coord")
    private int x_coord;

    @Column(name="y_coord")
    private int y_coord;

    @Column(name="text")
    private String text;

    @Column(name="completed")
    private Boolean completed;

    @Column(name="picture")
    private Blob picture;

    public Meeting(Expert e, User u, Time t, String loc, Boolean completed){
        expert = e;
        user = u;
        meeting_time = t;
        location = loc;
        completed = completed;
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
