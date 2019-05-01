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
@RequestMapping("/account")

public class AccountController {

    @Autowired
    private SecurityService securityService;
    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());
    private MeetingDAO meetingDAO = new MeetingDbDAO(new DBConnection());
    private QuestionDAO questionDAO = new QuestionDbDAO(new DBConnection());
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @RequestMapping(method = RequestMethod.GET)
    public String getAccountDetails(Model m){
//        context.scan("com.spe.breadcrumbs.security");
//        context.refresh();
//        securityService = context.getBean(SecurityService.class);

        String username = securityService.findLoggedInUsername();
        Expert expert = expertDAO.findByEmail(username);

        List<Meeting> expertsMeetings = meetingDAO.getMeetingsWithExpert(expert.getId());

        List<Meeting> usersOnFinalQuestion = new ArrayList<>();
        int noOfQuestions = questionDAO.getAllQuestions().size();

        for (Meeting meeting : expertsMeetings) {
            List<Question> questionsAnswered = questionDAO.getQuestionsAnswered(meeting.getUser().getId());
            if (questionsAnswered.size() >= noOfQuestions - 1) usersOnFinalQuestion.add(meeting);
        }

        m.addAttribute("expert", expert);
        m.addAttribute("meetings", expertsMeetings);
        m.addAttribute("usersOnFinalQuestion", usersOnFinalQuestion);

        return "views/account";
    }

}
