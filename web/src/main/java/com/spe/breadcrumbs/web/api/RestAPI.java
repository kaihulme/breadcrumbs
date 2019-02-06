package com.spe.breadcrumbs.web.api;


import com.spe.breadcrumbs.dao.QuestionDAO;
import com.spe.breadcrumbs.dao.QuestionDbDAO;
import com.spe.breadcrumbs.dao.UserDAO;
import com.spe.breadcrumbs.dao.UserDbDAO;
import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class RestAPI {
    private UserDAO userDAO = new UserDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();

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

    @RequestMapping(method = RequestMethod.GET,value = "questions")
    public List<Question> getQuestions(){
        return  questionDAO.getAllQuestions();
    }

    @RequestMapping(method = RequestMethod.GET,value = "questions/{id}")
    public Question getQuestion(@PathVariable Long id){
        return questionDAO.findById(id);
    }


}
