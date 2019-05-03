package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Meeting;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.web.DBConnection;
import com.spe.breadcrumbs.web.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/account")

public class AccountController {

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
    public String getAccountDetails(Model m){

        String username = securityService.findLoggedInUsername();
        Expert expert = expertDAO.findByEmail(username);

        List<Meeting> expertsMeetings = meetingDAO.getMeetingsWithExpert(expert.getId());

        m.addAttribute("expert", expert);
        m.addAttribute("meetings", expertsMeetings);
        m.addAttribute("meetingsWithUserAtEnd", getMeetingsWithUserAtEnd(expertsMeetings));

        return "views/account";
    }

}
