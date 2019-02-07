package com.spe.breadcrumbs.web.api;


import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")

public class RestAPI {
    private UserDAO userDAO = new UserDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();
    private ExpertDAO expertDAO = new ExpertDbDAO();

    //Users
    @RequestMapping(method = RequestMethod.GET,value = "users")
    public ResponseEntity getUsers(){
        return  new ResponseEntity<>(userDAO.getAllUsers(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "users/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        User match;
        match = userDAO.getUser(id);
        List<Question> questions = questionDAO.getAllQuestions();
        Quiz quiz = new Quiz("title");
        quiz.setQuestions(questions);
        if(match != null){
            match.setQuiz(quiz);
            return new ResponseEntity<>(match, HttpStatus.OK);
        }
        return new ResponseEntity(null,HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST,value = "users")
    public ResponseEntity addUser(@RequestBody User u){
        if(userDAO.addUser(u)) {
            return new ResponseEntity(null,HttpStatus.CREATED);
        }
        return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        if(userDAO.deleteUser(id)){
            return new ResponseEntity(null,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    //Experts
    @RequestMapping(method = RequestMethod.GET,value = "experts/{id}")
    public ResponseEntity getExpert(@PathVariable Long id){
        Expert e = expertDAO.getExpert(id);
        if(e != null){
            return new ResponseEntity<>(e,HttpStatus.OK);
        }
        return new ResponseEntity(null,HttpStatus.NOT_FOUND);
    }

    //Questions
    @RequestMapping(method = RequestMethod.GET,value = "questions")
    public ResponseEntity getQuestions(){
            return new ResponseEntity<>(questionDAO.getAllQuestions(),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,value = "questions/{id}")
    public ResponseEntity getQuestion(@PathVariable Long id) {
        Question q = questionDAO.findById(id);
        if(q != null){
            return new ResponseEntity<>(q,HttpStatus.OK);
        }else{
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
    }


}
