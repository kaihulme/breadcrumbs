package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import com.spe.breadcrumbs.web.DBConnection;
import com.spe.breadcrumbs.web.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/meetings")

public class MeetingsController {

    @Autowired
    private SecurityService securityService;
    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());
    private MeetingDAO meetingDAO = new MeetingDbDAO(new DBConnection());
    private QuestionDAO questionDAO = new QuestionDbDAO(new DBConnection());
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    private List<Meeting> getMeetingsWithUserAtEnd(List<Meeting> expertsMeetings) {
        List<Meeting> meetingsWithUserAtEnd = new ArrayList<>();
        int noOfQuestions = questionDAO.getAllQuestions().size();
        for (Meeting meeting : expertsMeetings) {
            List<Question> questionsAnswered = questionDAO.getQuestionsAnswered(meeting.getUser().getId());
            if (questionsAnswered.size() >= noOfQuestions - 1) meetingsWithUserAtEnd.add(meeting);
        }
        return meetingsWithUserAtEnd;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getMeetings(Model m){

        String username = securityService.findLoggedInUsername();
        Expert expert = expertDAO.findByEmail(username);
        List<Meeting> expertsMeetings = meetingDAO.getUpcomingMeetingsWithExpert(expert.getId());

        m.addAttribute("expertsMeetings", expertsMeetings);
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd(expertsMeetings));

        return "views/meetings";
    }

    @RequestMapping(method = RequestMethod.GET, value="/{user_id}")
    public String viewMeeting(Model m, @PathVariable Long user_id) {

        String username = securityService.findLoggedInUsername();
        Expert expert = expertDAO.findByEmail(username);
        List<Meeting> expertsMeetings = meetingDAO.getUpcomingMeetingsWithExpert(expert.getId());

        m.addAttribute("selectedMeeting", meetingDAO.getMeeting(user_id));
        m.addAttribute("expertsMeetings", expertsMeetings);
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd(expertsMeetings));

        return "views/meetings_meeting";
    }

    @RequestMapping(method = RequestMethod.GET, value="/completed")
    public String getMeetingsCompleted(Model m){

        String username = securityService.findLoggedInUsername();
        Expert expert = expertDAO.findByEmail(username);
        List<Meeting> expertsMeetings = meetingDAO.getUpcomingMeetingsWithExpert(expert.getId());

        m.addAttribute("meetingsCompleted", meetingDAO.getCompletedMeetingsWithExpert(expert.getId()));
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd(expertsMeetings));

        return "views/meetings_completed";
    }

}
