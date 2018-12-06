package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/participants")

public class UserController {
    private UserDAO userDAO = new UserListDAO();
    private QuestionDAO questionDAO = new QuestionListDAO();

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
        Question q = u.getQuiz().findQuestion(questionId);
        m.addAttribute("question",q);
        return "views/participants_userProfile_question";

    }


//    private UserDAO users = new UserListDAO();
//    @RequestMapping(method = RequestMethod.GET)
//    public List<User> getAll(){
//        return users.getAllUsers();
//    }
//    //get a user's profile data
//    @RequestMapping(method = RequestMethod.GET,value = "{id}")
//    public ResponseEntity get(@PathVariable Long id){
//        User match;
//        match = users.getUser(id);
//        if(match != null){
//            return new ResponseEntity<>(match, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @RequestMapping(method = RequestMethod.GET,value = "/email/{email}")
//    public ResponseEntity getByEmail(@PathVariable String email){
//        User match = users.getByEmail(email);
//        if(match != null){
//            return new ResponseEntity<>(match, HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity add(@RequestBody User u){
//        if(users.add(u)){
//            return new ResponseEntity<>(null,HttpStatus.CREATED);
//        }else{
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @RequestMapping(method =RequestMethod.PUT,value = "{id}")
//    public ResponseEntity update(@PathVariable Long id,@RequestBody User u){
//        if (users.update(id,u)) {
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Delete a User
//    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
//    public ResponseEntity delete(@PathVariable Long id) {
//
//        boolean result = users.delete(id);
//
//        if (result) {
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }



}
