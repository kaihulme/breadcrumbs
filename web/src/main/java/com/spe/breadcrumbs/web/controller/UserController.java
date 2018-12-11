package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/participants")

public class UserController {
    private UserDAO userDAO = new UserDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();

    @RequestMapping(method = RequestMethod.GET)
    public String participants(Model m){
        m.addAttribute("users",userDAO.getAllUsers());
        return "views/participants";
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public String getUserDetail(@PathVariable Long id,Model m){
        User match;
        match = userDAO.getUser(id);
        List<Question> questions = questionDAO.getAllQuestions();
        Quiz quiz = new Quiz("title");
        quiz.setQuestions(questions);
        if(match != null) match.setQuiz(quiz);
        m.addAttribute("user",match);
        return "views/participants_userProfile";
    }

    @RequestMapping(method = RequestMethod.GET,value = "{userId}/questions/{questionId}")
    public String getQuestionDetail(@PathVariable Long userId,@PathVariable Long questionId,Model m){
        User u = userDAO.getUser(userId);
        Quiz quiz = new Quiz("title");
        quiz.setQuestions(questionDAO.getAllQuestions());
        u.setQuiz(quiz);
        Question q = u.getQuiz().findQuestion(questionId);
        Choice c1 = new Choice(q,"deer",false);
        Choice c2 = new Choice(q,"kidney",true);
        Choice c3 = new Choice(q,"hat",false);
        q.setAttempts(Arrays.asList(c1,c2,c3));
        m.addAttribute("question",q);
        return "views/participants_userProfile_question";
    }




}
