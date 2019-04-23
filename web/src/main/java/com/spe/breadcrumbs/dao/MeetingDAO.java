package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Meeting;

import java.util.List;

public interface MeetingDAO {
    boolean createMeeting(Meeting m);
    boolean updateMeeting(Long userId,Long expertId,Meeting m);
    boolean deleteMeeting(Long userId,Long expertId);
    List<Meeting> getMeetings();
    Meeting getMeeting(Long userId,Long expertId);
}
