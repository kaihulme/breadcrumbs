package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Meeting;

import java.util.List;

public interface MeetingDAO {
    boolean createMeeting(Meeting m);
    boolean updateMeeting(Long userId,Meeting m);
    boolean deleteMeeting(Long userId);
    List<Meeting> getMeetings();
    List<Meeting> getMeetingsWithExpert(Long expertId);
    Meeting getMeeting(Long userId);
}
