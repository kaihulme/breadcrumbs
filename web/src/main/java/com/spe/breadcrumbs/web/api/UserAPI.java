package com.spe.breadcrumbs.web.api;

import com.spe.breadcrumbs.dao.QuestionDAO;
import com.spe.breadcrumbs.dao.QuestionDbDAO;
import com.spe.breadcrumbs.dao.UserDAO;
import com.spe.breadcrumbs.dao.UserDbDAO;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/users")

public class UserAPI {
    private UserDAO userDAO = new UserDbDAO();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUsers(@RequestParam(value = "code",required = false,defaultValue = "") String code){
        if(code.equals("")) return new ResponseEntity<>(userDAO.getAllUsers(), HttpStatus.OK);
        else{
            User match;
            match = userDAO.getByCode(code);
            if(match != null){
                return new ResponseEntity<>(match, HttpStatus.OK);
            }
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        User match;
        match = userDAO.getUserWithQuiz(id) ;
        if(match != null){
            return new ResponseEntity<>(match, HttpStatus.OK);
        }
        return new ResponseEntity(null,HttpStatus.NOT_FOUND);
    }

    //update the user details with that id
    @RequestMapping(method = RequestMethod.PUT,value = "{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody User u){
        if(userDAO.update(id,u)){
            return new ResponseEntity(null,HttpStatus.OK);
        }else{
            return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody User u){
        if(userDAO.addUser(u)) {
            return new ResponseEntity(null,HttpStatus.CREATED);
        }
        return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        if(userDAO.deleteUser(id)){
            return new ResponseEntity(null,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

}
