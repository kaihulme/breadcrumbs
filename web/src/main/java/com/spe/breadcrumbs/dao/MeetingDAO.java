package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Meeting;

import java.util.List;

public interface MeetingDAO {

    boolean createMeeting(Meeting m);

    boolean updateMeeting(Long userId,Meeting m);

    boolean updateMeetingLocation(Long userId, Meeting m);

    boolean completeMeeting(Long userId, String text);

    boolean deleteMeeting(Long userId);

    List<Meeting> getMeetings();

    List<Meeting> getMeetingsWithExpert(Long expertId);
    List<Meeting> getUpcomingMeetingsWithExpert(Long expertId);
    List<Meeting> getCompletedMeetingsWithExpert(Long expertId);

    Meeting getMeeting(Long userId);

}
