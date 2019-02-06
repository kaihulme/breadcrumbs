package com.spe.breadcrumbs.web.api;


import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class RestAPI {
    private UserDAO userDAO = new UserDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();
    private ExpertDAO expertDAO = new ExpertDbDAO();

    @RequestMapping(method = RequestMethod.GET,value = "users")
    public List<User> getUsers(){
        return  userDAO.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET,value = "users/{id}")
    public User getUser(@PathVariable Long id){
        User match;
        match = userDAO.getUser(id);
        List<Question> questions = questionDAO.getAllQuestions();
        Quiz quiz = new Quiz("title");
        quiz.setQuestions(questions);
        if(match != null) match.setQuiz(quiz);
        return match;
    }

    @RequestMapping(method = RequestMethod.POST,value = "users")
    public void addUser(User u){
        userDAO.add(u);
    }
    @RequestMapping(method = RequestMethod.GET,value = "experts/{id}")
    public Expert getExpert(@PathVariable Long id){
        return expertDAO.getExpert(id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "questions")
    public List<Question> getQuestions(){
        return  questionDAO.getAllQuestions();
    }

    @RequestMapping(method = RequestMethod.GET,value = "questions/{id}")
    public Question getQuestion(@PathVariable Long id){
        return questionDAO.findById(id);
    }


}
