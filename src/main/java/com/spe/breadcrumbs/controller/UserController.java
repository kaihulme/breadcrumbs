package com.spe.breadcrumbs.controller;

import com.spe.breadcrumbs.dao.UserDAO;
import com.spe.breadcrumbs.dao.UserListDAO;
import com.spe.breadcrumbs.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController

@RequestMapping("/participants")
public class UserController {
    private UserDAO users = new UserListDAO();

    public List<User> getAll(){
        return users.getAllUsers();
    }
    //get a user's profile data
    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public ResponseEntity get(@PathVariable long id){
        User match;
        match = users.getUser(id);
        if(match != null){
            return new ResponseEntity<>(match, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/email/{email}")
    public ResponseEntity getByEmail(@PathVariable String email){
        User match = users.getByEmail(email);
        if(match != null){
            return new ResponseEntity<>(match, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity add(@RequestBody User u){
        if(users.add(u)){
            return new ResponseEntity<>(null,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method =RequestMethod.PUT,value = "{id}")
    public ResponseEntity update(@PathVariable long id,@RequestBody User u){
        if (users.update(id,u)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete a User
    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public ResponseEntity delete(@PathVariable long id) {

        boolean result = users.delete(id);

        if (result) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



}
