package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import com.spe.breadcrumbs.web.DBConnection;
import com.spe.breadcrumbs.web.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/participants")


public class UserController {

    private UserDAO userDAO = new UserDbDAO(new DBConnection());
    private QuestionDAO questionDAO = new QuestionDbDAO(new DBConnection());
    private QuizDAO quizDAO = new QuizDbDAO(new DBConnection());
    private AttemptDAO attemptDAO = new AttemptDbDAO(new DBConnection());
    private MeetingDAO meetingDAO = new MeetingDbDAO(new DBConnection());
    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());

    @Autowired
    private SecurityService securityService;

    private List<Meeting> getCurrentExpertsMeetings() {
        String username = securityService.findLoggedInUsername();
        Expert currentExpert = expertDAO.findByEmail(username);
        return meetingDAO.getMeetingsWithExpert(currentExpert.getId());
    }

    @RequestMapping(method = RequestMethod.GET)
    public String participants(Model m){

        List<User> users = userDAO.getAllUsers();
        User match = userDAO.getUserWithQuiz(users.get(0).getId());
        List<Question> questions = match.getQuiz().getQuestions();
        for (User u : users) {

            //            int completed = 0;


            List<Question> completed = questionDAO.getQuestionsAnswered(u.getId());


//            for (Question q : questions) {
//                if (attemptDAO.getAttempts(q.getId(),u.getId()).size() > 0) {
//                    completed++;
//                }
//            }

            float progress = 100 * ((float)completed.size() / (float)questions.size());
            u.setProgress(progress);

        }

        m.addAttribute("users", users);
        m.addAttribute("expertsMeetings", getCurrentExpertsMeetings());
        return "views/participants";
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public String getUserDetail(@PathVariable Long id,Model m){

        int completed = 0;

        User match = userDAO.getUserWithQuiz(id);
        List<Question> questions = match.getQuiz().getQuestions();
        for(Question q: questions){
            //get no of attempts plus score
            int noOfAttempts = attemptDAO.getAttempts(q.getId(),id).size();
            int score;
            if (noOfAttempts == 1) score = 100;
            else if (noOfAttempts == 2) score = 50;
            else if (noOfAttempts == 3) score = 25;
            else score = 0;
            q.setNoOfAttempts(noOfAttempts);
            q.setScore(score);
            if (score > 0) completed++;

        }

        float progress = 100 * ((float)completed / (float)questions.size());

        match.setProgress(progress);
        m.addAttribute("user", match);
        m.addAttribute("expertsMeetings", getCurrentExpertsMeetings());

        return "views/participants_userProfile";
    }

    @RequestMapping(method = RequestMethod.GET,value = "{userId}/questions/{questionId}")
    public String getQuestionDetail(@PathVariable Long userId,@PathVariable Long questionId,Model m){
        User u = userDAO.getUserWithQuiz(userId);
        Quiz quiz = u.getQuiz();
        Question q = quiz.findQuestion(questionId);
        List<Choice> choices = questionDAO.getChoices(questionId);
        q.setChoices(choices);
        List<Choice> attempts = attemptDAO.getAttempts(questionId,userId);
        q.setAttempts(attempts);
        m.addAttribute("question",q);
        m.addAttribute("expertsMeetings", getCurrentExpertsMeetings());
        return "views/participants_userProfile_question";
    }

}
